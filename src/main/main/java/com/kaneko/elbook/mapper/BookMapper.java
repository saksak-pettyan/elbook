package com.kaneko.elbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kaneko.elbook.domain.Book;

/**
 * 書籍テーブル T_BOOK 用の Mapper インタフェース。
 * 実際の SQL は resources/mappers/BookMapper.xml に記述します。
 */
@Mapper
public interface BookMapper {

	/** 全件取得（初期表示用）*/
	List<Book> findAll();

	/** 書籍名による部分一致検索 */
	List<Book> findByTitleLike(@Param("title") String title);

	/** 主キー（BOOK_ID）で1権取得（詳細表示用）*/
	Book findById(@Param("bookId") Long bookId);

	/** 新規登録 */
	void insert(Book book);

	/** 更新 */
	int update(Book book);

	/**
	 * 貸出処理：在庫がある場合に在庫を1減らし、貸出中冊数を1増やす。
	 * @return 更新件数（1なら成功、0なら在庫不足）
	 */
	int rentBook(@Param("bookId") Long bookId);

	/**
	 * 返却処理：貸出中冊数がある場合に在庫を1増やし、貸出中冊数を1減らす。
	 * @return 更新件数（1なら成功、0なら不正状態）
	 */
	int returnBook(@Param("bookId") Long bookId);

}
