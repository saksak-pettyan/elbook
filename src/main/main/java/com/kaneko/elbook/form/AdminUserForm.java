package com.kaneko.elbook.form;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 管理：利用者一覧・詳細用のフォーム。
 * 画面仕様書 SCR-ADMIN-USER-LIST / SCR-ADMIN-USER-DETAIL をベース。
 */
@Data
public class AdminUserForm {
	
	/** 利用者ID（更新時のみ）*/
	private Long userId;
	
	/** 氏名 */
	@NotBlank(message = "氏名は必須です。")
	@Size(max = 100, message = "氏名は100文字以内で入力してください。")
	private String userName;
	
	/** ログインID */
	@NotBlank(message = "ログインIDは必須です。")
	@Size(max = 50, message = "ログインIDは50文字以内で入力してください。")
	private String loginId;
	
	/** 状態（有効／無効）*/
	@NotBlank(message = "状態を選択してください。")
	private String status;
	
	/** 作成日時（表示専用） */
    private LocalDateTime createdAt;

    /** 更新日時（表示専用） */
    private LocalDateTime updatedAt;
    
    // --- ここからはパスワード再設定用（仕様の保管として提案部分）---
    
    /** 新しいパスワード（再設定時のみ仕様）*/
    @Size(max = 100, message = "パスワードは100文字以内で入力してください。")
    private String newPassword;
    
    /** 新しいパスワード（確認）*/
    @Size(max = 100,message = "パスワード（確認は）100文字以内で入力してください。")
    private String newPasswordConfirm;
}
