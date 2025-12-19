package com.kaneko.elbook.domain;

import lombok.Data;

/**
 * 書籍テーブル T_BOOK に対応するドメインクラス。
 * 画面仕様書【管理者向け】 SCR-ADMIN-BOOK-LIST で使用する項目です。
 */
@Data
public class Book {

	/** 書籍ID（BOOK_ID） */
	private Long bookId;

	/** 書籍名（TITLE） */
	private String title;

	/** 著者名（AUTHOR） */
	private String author;

	/** 保管場所（LOCATION） */
	private String location;

	/** 総冊数（TOTAL_COUNT） */
	private Integer totalCount;

	/** 在庫冊数（STOCK_COUNT） */
	private Integer stockCount;

	/** 貸出冊数（RENTAL_COUNT） */
	private Integer rentalCount;
}
