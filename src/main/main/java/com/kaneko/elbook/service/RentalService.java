package com.kaneko.elbook.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaneko.elbook.domain.Rental;
import com.kaneko.elbook.dto.UserRentalRow;
import com.kaneko.elbook.mapper.BookMapper;
import com.kaneko.elbook.mapper.RentalMapper;

@Service
public class RentalService {

	private final RentalMapper rentalMapper;
	private final BookMapper bookMapper;

	public RentalService(RentalMapper rentalMapper, BookMapper bookMapper) {
		this.rentalMapper = rentalMapper;
		this.bookMapper = bookMapper;
	}

	/** 貸出登録（成功: true / 在庫なし等: false） */
	@Transactional
	public boolean rentBook(Long bookId, Long userId) {

		// 1) 在庫更新（在庫がなければ0件更新 → 失敗）
		int updated = bookMapper.rentBook(bookId);
		if (updated != 1) {
			return false;
		}

		// 2) 貸出履歴を登録（通常1冊想定）
		Rental r = new Rental();
		r.setUserId(userId);
		r.setBookId(bookId);
		r.setRentalDate(LocalDate.now());
		r.setReturnDate(null);
		r.setRentalCount(1);
		r.setStatus("貸出中");

		rentalMapper.insertRental(r);

		return true;
	}

	/** 返却（自分の貸出のみ返却可能。成功: true / 対象なし: false） */
	@Transactional
	public boolean returnBook(Long rentalId, Long userId) {

		// 1) 貸出レコードを返却済へ（自分の貸出＆貸出中のみ）
		int updatedRental = rentalMapper.markReturned(rentalId, userId);
		if (updatedRental != 1) {
			return false;
		}

		// 2) 対象レンタルの BOOK_ID をDBから取得（画面入力に依存しない）
		Long bookId = rentalMapper.selectBookIdByRentalIdAndUserId(rentalId, userId);
		if (bookId == null) {
			// markReturnedが成功している前提では通常起きないはずですが、整合性担保のため明示します
			throw new IllegalStateException("返却対象のBOOK_IDが取得できませんでした。rentalId=" + rentalId);
		}

		// 3) 書籍在庫を戻す（更新件数を確認）
		int updatedBook = bookMapper.returnBook(bookId);
		if (updatedBook != 1) {
			// ここで例外にすると @Transactional により 1) の更新もロールバックされます
			throw new IllegalStateException("返却による在庫更新に失敗しました。bookId=" + bookId);
		}

		return true;
	}

	public List<UserRentalRow> getMyCurrentRentals(Long userId) {
		return rentalMapper.selectMyRentalCurrent(userId);
	}

	public List<UserRentalRow> getMyRentalHistory(Long userId) {
		return rentalMapper.selectMyRentalHistory(userId);
	}
}
