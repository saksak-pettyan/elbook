package com.kaneko.elbook.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kaneko.elbook.domain.AdminAccount;

@Mapper
public interface AdminAccountMapper {

    /**
     * 管理アカウントを主キー（ADMIN_ID）で取得する。
     *
     * @param adminId 管理者ID（通常は "ADMIN" 固定）
     * @return 該当する管理アカウント。存在しない場合は null。
     */
    AdminAccount selectById(@Param("adminId") String adminId);

    /**
     * 管理者パスワードを更新し、初期設定フラグを ON にする。
     *
     * @param adminId     管理者ID
     * @param passwordHash ハッシュ化済みパスワード
     */
    void updatePasswordAndInitialize(
            @Param("adminId") String adminId,
            @Param("passwordHash") String passwordHash
    );
}