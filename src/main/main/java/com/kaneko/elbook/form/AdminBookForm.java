package com.kaneko.elbook.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

/**
 * SCR-ADMIN-BOOK-DETAIL（管理：書籍詳細・編集）の入力フォーム
 * 画面仕様書の項目ID（bookName, authorName 等）に対応させています。
 */
@Data
public class AdminBookForm {
	/** 書籍ID（新規時は null） */
	private Long bookId;

	/** 書籍名（必須） */
	@NotBlank(message = "書籍名は必須です。")
	private String bookName;

	/** 著者名（必須） */
	@NotBlank(message = "著者名は必須です。")
	private String authorName;

	/** 保管場所（必須）*/
	@NotBlank(message = "保管場所は必須です。")
	private String location;

	/** 総冊数（必須・1以上の整数） */
	@NotNull(message = "総冊数は必須です。")
	@Min(value = 1, message = "総冊数は１以上の整数で入力してください。")
	private Integer totalCount;

	/** 在庫冊数（表示のみ・編集不可） */
	private Integer stockCount;

	/** 貸出冊数（表示のみ・編集不可） */
	private Integer rentalCount;
}
