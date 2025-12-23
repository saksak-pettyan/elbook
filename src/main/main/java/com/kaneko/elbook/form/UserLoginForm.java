package com.kaneko.elbook.form;
/**
 * 一般利用者ログインフォーム.
 */

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
@Data
public class UserLoginForm {
	
	@NotBlank(message = "ログインIDを入力してください。")
	private String loginId;
	
	@NotBlank(message = "パスワードを入力してください。")
	private String password;
}
