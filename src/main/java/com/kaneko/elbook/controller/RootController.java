package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.kaneko.elbook.service.AdminAccountService;

@Controller
public class RootController {

	private final AdminAccountService adminAccountService;

	public RootController(AdminAccountService adminAccountService) {
		this.adminAccountService = adminAccountService;
	}

	@GetMapping("/")
	public String root(HttpSession session) {
		// 既存のセッション情報をいったん破棄して、クリーンな状態にする
		session.invalidate();

		// 初期設定済みかどうかを判定
		boolean initialized = adminAccountService.isInitialized();

		// 初期設定がまだなら初期設定画面へ
		if (!initialized) {
			return "redirect:/init";
		}
		return "redirect:/user/login";
	}

}
