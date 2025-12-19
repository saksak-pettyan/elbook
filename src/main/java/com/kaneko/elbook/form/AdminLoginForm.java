package com.kaneko.elbook.form;

import jakarta.validation.constraints.NotBlank;

/**
 * 管理ログイン画面（SCR-ADMIN-LOGIN）の入力フォーム。
 * 画面の1項目（パスワード）を受け取るためのクラスです。
 */
public class AdminLoginForm {

    @NotBlank(message = "パスワードを入力してください。")
    private String adminPassword;

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }
}
