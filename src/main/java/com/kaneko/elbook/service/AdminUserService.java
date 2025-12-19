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

	/**
	 * 一般利用者ログイン用の認証処理。
	 *
	 * @param loginId    画面から入力されたログインID
	 * @param rawPassword 画面から入力された平文パスワード
	 * @return 認証成功時は User エンティティ、失敗時は null
	 */
	public User authenticateUser(String loginId, String rawPassword) {

		// 1. 入力チェック（null や空文字なら即失敗）
		if (loginId == null || loginId.isBlank() || rawPassword == null || rawPassword.isBlank()) {
			return null;
		}

		// 2. ログインIDでユーザー取得
		User user = userMapper.selectByLoginId(loginId);
		if (user == null) {
			// 該当ユーザーが存在しない
			return null;
		}

		// 3. 状態が「有効」以外ならログインさせない
		if (!"有効".equals(user.getStatus())) {
			return null;
		}

		// 4.DBに保存されているパスワードハッシュを取得
		String passwordHash = user.getPasswordHash();
		if (passwordHash == null || passwordHash.isBlank()) {
			return null;
		}

		// 5. 平文パスワードとハッシュ値を比較
		boolean matches = passwordEncoder.matches(rawPassword, passwordHash);
		if (!matches) {
			return null;
		}

		// 6. ここまでこれば認証成功
		return user;
	}
}
