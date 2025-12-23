package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.form.UserLoginForm;
import com.kaneko.elbook.service.UserService;

/**
 * 一般利用者ログイン用コントローラ.
 */
@Controller
public class UserLoginController {

	private final UserService userService;

	public UserLoginController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 利用者ログイン画面表示（GET）
	 */
	@GetMapping("/user/login")
	public String showUserLogin(UserLoginForm form, HttpSession session) {
		// 念のため利用者セッションをクリア
		session.removeAttribute("LOGIN_USER");
		return "user/user_login";
	}

	/**
	 * 利用者ログイン処理（POST /user/login）
	 */
	@PostMapping("/user/login")
	public String doUserLogin(@Valid UserLoginForm form, BindingResult bindingResult, HttpSession session,
			Model model) {

		// 1. 入力チェック（必須チェックなど）
		if (bindingResult.hasErrors()) {
			return "user/user_login";
		}

		// 2. 認証処理
		User user = userService.authenticate(form.getLoginId(), form.getPassword());
		if (user == null) {
			model.addAttribute("errorMessage", "ログインIDまたはパスワードが正しくありません。");
			return "user/user_login";
		}

		// 3. 認証成功 → セッションに利用者情報を保存
		session.setAttribute("LOGIN_USER", user);

		// 4. 利用者メニューへリダイレクト
		return "redirect:/user/menu";
	}

}
