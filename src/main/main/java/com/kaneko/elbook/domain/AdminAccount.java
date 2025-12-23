package com.kaneko.elbook.domain;

import lombok.Data;

@Data
public class AdminAccount {

    /**
     * 管理者ID
     * T_ADMIN_ACCOUNT.ADMIN_ID に対応
     */
    private String adminId;

    /**
     * パスワードハッシュ
     * T_ADMIN_ACCOUNT.PASSWORD_HASH に対応
     */
    private String passwordHash;

    /**
     * 初期設定完了フラグ
     * T_ADMIN_ACCOUNT.IS_INITIALIZED に対応
     * 0: 未実施 / 1: 実施済み の想定
     */
    private Boolean initialized;
}