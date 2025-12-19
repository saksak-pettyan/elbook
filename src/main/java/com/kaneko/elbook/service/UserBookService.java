package com.kaneko.elbook.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.mapper.BookMapper;

@Service
public class UserBookService {

	private final BookMapper bookMapper;

	public UserBookService(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	/** タイトル部分一致検索（null なら全件） */
	public List<Book> searchBooks(String title) {
		if (title == null || title.isBlank()) {
			return bookMapper.findAll();
		}
		return bookMapper.findByTitleLike(title);
	}

	/** 書籍1件取得 */
	public Book getBook(Long bookId) {
		return bookMapper.findById(bookId);
	}

	/**
	 * 利用者の貸出処理。
	 * 現時点では T_BOOK の在庫・貸出数のみ更新する。
	 *
	 * @param bookId 書籍ID
	 * @param userId 利用者ID（※将来の貸出履歴登録用。現時点では未使用）
	 * @return true: 貸出成功 / false: 在庫不足
	 */
	@Transactional
	public boolean rentBook(Long bookId, Long userId) {
	    int updated = bookMapper.rentBook(bookId);
		// ※ 今後 T_RENTAL に userId を insert する想定
		return updated == 1;
	}
}
