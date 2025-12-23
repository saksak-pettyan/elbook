package com.kaneko.elbook.config;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 一般利用者ログイン（セッション）チェック用Interceptor。
 *
 * セッションキー: LOGIN_USER (User)
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		HttpSession session = request.getSession(false);
		Object loginUser = (session == null) ? null : session.getAttribute("LOGIN_USER");

		if (loginUser != null) {
			return true;
		}

		response.sendRedirect(request.getContextPath() + "/user/login");
		return false;
	}
}
