package com.kaneko.elbook.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * T_USER（利用者テーブル）に対応するドメインクラス。
 * テーブル定義書「T_USER（利用者テーブル）」準拠。
 */
@Data
public class User {
	
	/** 利用者ID（PK）*/
	private Long userId;
	
	/** 氏名 */
    private String userName;

    /** ログインID（一意） */
    private String loginId;

    /** パスワードハッシュ（平文は保持しない） */
    private String passwordHash;

    /** 状態（有効／無効） */
    private String status;

    /** 作成日時 */
    private LocalDateTime createdAt;

    /** 更新日時 */
    private LocalDateTime updatedAt;
}
