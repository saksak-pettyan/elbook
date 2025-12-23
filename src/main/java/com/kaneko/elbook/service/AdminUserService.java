package com.kaneko.elbook.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.form.AdminUserForm;
import com.kaneko.elbook.mapper.UserMapper;

@Service
public class AdminUserService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public AdminUserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 利用者一覧取得（氏名部分一致検索）。
	 */
	public List<User> searchUsers(String userName) {
		return userMapper.selectList(userName);
	}

	/**
	 * 利用者1件取得。
	 */
	public User getUser(Long userId) {
		return userMapper.selectById(userId);
	}

	/**
	 * 新規利用者登録。
	 * ※パスワードは、フォームの newPassword をハッシュして保存する前提。
	 */
	@Transactional
	public void registerNewUser(AdminUserForm form) {
		User user = new User();
		user.setUserName(form.getUserName());
		user.setLoginId(form.getLoginId());
		user.setStatus(form.getStatus());

		// パスワード（仕様が明確ではないため、newPassword をそのまま使う形で保管）
		String rawPassword = form.getNewPassword();
		String hash = passwordEncoder.encode(rawPassword);
		user.setPasswordHash(hash);

		userMapper.insert(user);
	}

	/**
	 * 既存利用者更新（氏名・ログインID・状態）。
	 */
	@Transactional
	public void updateUser(AdminUserForm form) {
		User user = new User();
		user.setUserId(form.getUserId());
		user.setUserName(form.getUserName());
		user.setLoginId(form.getLoginId());
		user.setStatus(form.getStatus());

		userMapper.update(user);
	}

	/**
	 * パスワード再設定。
	 */
	@Transactional
	public void resetPassword(Long userId, String newPassword) {
		String hash = passwordEncoder.encode(newPassword);
		userMapper.updatePassword(userId, hash);
	}
}