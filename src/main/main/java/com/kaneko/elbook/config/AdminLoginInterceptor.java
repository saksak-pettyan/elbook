package com.kaneko.elbook.config;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理者ログイン（セッション）チェック用Interceptor。
 *
 * セッションキー: LOGIN_ADMIN (Boolean)
 */
@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws IOException {
		HttpSession session = request.getSession(false);
		Boolean isAdminLogin = (session == null) ? null : (Boolean) session.getAttribute("LOGIN_ADMIN");

		if (Boolean.TRUE.equals(isAdminLogin)) {
			return true;
		}

		response.sendRedirect(request.getContextPath() + "/admin/login");
		return false;
	}
}
