package com.kaneko.elbook.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.mapper.UserMapper;

@Service
public class UserService {
	
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
     * 利用者ログイン認証
     * 成功: User を返す / 失敗: null
     */
	public User authenticate(String loginId,String rawPassword) {
		
		User user = userMapper.selectByLoginId(loginId);
		if(user==null) {
			return null;
		}
		
		// 状態が「有効」のみ通す（仕様が違うならここは変更）
		if(user.getStatus()==null||!"有効".equals(user.getStatus())) {
			return null;
		}
		
		String hash = user.getPasswordHash();
        if (hash == null || hash.isEmpty()) {
            return null;
        }

        if (!passwordEncoder.matches(rawPassword, hash)) {
            return null;
        }

        return user;
	}
}
