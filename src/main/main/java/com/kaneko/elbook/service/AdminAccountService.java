package com.kaneko.elbook.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaneko.elbook.domain.AdminAccount;
import com.kaneko.elbook.mapper.AdminAccountMapper;

@Service
public class AdminAccountService {

    private final AdminAccountMapper adminAccountMapper;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountService(AdminAccountMapper adminAccountMapper,
                               PasswordEncoder passwordEncoder) {
        this.adminAccountMapper = adminAccountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 初期設定済みかどうかを判定する。
     * T_ADMIN_ACCOUNT の ADMIN 行の IS_INITIALIZED を見る。
     */
    public boolean isInitialized() {
        AdminAccount admin = adminAccountMapper.selectById("ADMIN");
        if (admin == null) {
            // レコードがないのは想定外なので、開発中は例外にしておく
            throw new IllegalStateException("管理アカウント（ADMIN）が T_ADMIN_ACCOUNT に存在しません。");
        }
        Boolean flag = admin.getInitialized();
        return Boolean.TRUE.equals(flag);
    }

    /**
     * 管理者パスワードを設定し、初期設定を完了させる。
     *
     * @param rawPassword 平文パスワード
     */
    public void initializeAdminPassword(String rawPassword) {
        // 平文パスワードをハッシュ化
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // ADMIN 行の PASSWORD_HASH と IS_INITIALIZED を更新
        adminAccountMapper.updatePasswordAndInitialize("ADMIN", encodedPassword);
    }
    /**
     * 管理者ログイン用の認証処理。
     *
     * @param rawPassword 入力された平文パスワード
     * @return 認証成功なら true、失敗なら false
     */
    public boolean authenticateAdmin(String rawPassword) {
        // 1. ADMIN 行を DB から取得
        AdminAccount admin = adminAccountMapper.selectById("ADMIN");
        if (admin == null) {
            // 管理者レコード自体がない場合は失敗扱い
            return false;
        }

        // 2. DB に保存されているハッシュ値を取り出す
        String passwordHash = admin.getPasswordHash();
        if (passwordHash == null || passwordHash.isEmpty()) {
            // パスワードが設定されていない場合も失敗
            return false;
        }

        // 3. PasswordEncoder で平文とハッシュを比較
        return passwordEncoder.matches(rawPassword, passwordHash);
    }

}
