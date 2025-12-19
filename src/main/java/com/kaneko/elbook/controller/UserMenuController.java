package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kaneko.elbook.domain.User;

/**
 * SCR-USER-MENU（一般利用者メニュー）
 */
@Controller
public class UserMenuController {

	/** 利用者メニュー表示 */
	@GetMapping("/user/menu")
	public String showUserMenu(HttpSession session, Model model) {

		// ログイン済みユーザをセッションから取得
		User loginUser = (User) session.getAttribute("LOGIN_USER");
		if (loginUser == null) {
			// 未ログインならログイン画面へ
			return "redirect:/user/login";
		}
		
		// 2. 画面にログイン中ユーザ情報を渡す
		model.addAttribute("loginUser", loginUser);
		
		// 3. メニュー画面を表示
		return "user/user_menu";
	}

	/**利用者ログアウト処理*/
	@PostMapping("/user/logout")
	public String logout(HttpSession session) {

		// セッション破棄
		session.invalidate();

		// ログイン選択画面へ戻る
		return "redirect:/user/login";
	}

}
