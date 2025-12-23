package com.kaneko.elbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kaneko.elbook.domain.User;

@Mapper
public interface UserMapper {
	
	/**
     * 利用者一覧取得（氏名で部分一致検索）。
     * 検索条件なしの場合は全件。
     */
	List<User> selectList(@Param("userNameLike") String userNameLike);
	
	/**
     * 利用者を主キー（USER_ID）で取得。
     */
	User selectById(@Param("userId") Long UserId);
	
	/**
     * ログインIDから利用者を取得（重複チェックなど用）。
     */
	User selectByLoginId(@Param("loginId") String loginId);
	
	/**
     * 利用者新規登録。
     * USER_ID は DB 側の AUTO_INCREMENT を想定。
     */
    void insert(User user);

    /**
     * 利用者情報更新（氏名・ログインID・状態のみ）。
     */
    void update(User user);

    /**
     * パスワードハッシュを更新。
     */
    void updatePassword(
            @Param("userId") Long userId,
            @Param("passwordHash") String passwordHash);
}
