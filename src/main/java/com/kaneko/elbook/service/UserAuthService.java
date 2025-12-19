package com.kaneko.elbook.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.mapper.UserMapper;

/**
 * 一般利用者の認証処理を行うサービス.
 */
@Service
public class UserAuthService {

	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public UserAuthService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 利用者ログイン認証.
	 *
	 * @param loginId    入力されたログインID
	 * @param rawPassword 入力された平文パスワード
	 * @return 認証成功時：ユーザ情報、失敗時：null
	 */
	public User authenticate(String loginId, String rewPassword) {

		// 1.ログインID から T_USER を 1件取得
		User user = userMapper.selectByLoginId(loginId);
		if (user == null) {
			// 該当ユーザーがいなければ失敗
			return null;
		}

		// 2. ステータスが「有効」でなければログインさせない
		if (!"有効".equals(user.getStatus())) {
			return null;
		}

		// 3. パスワードハッシュを取得
		String hash = user.getPasswordHash();
		if (hash == null || hash.isEmpty()) {
			return null;
		}

		boolean matches = passwordEncoder.matches(rewPassword, hash);
		if (!matches) {
			return null;
		}

		// 5. すべて OK なら User を返却
		return user;
	}
}
