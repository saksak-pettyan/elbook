package com.kaneko.elbook.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.service.AdminUserService;

/**
 * SCR-ADMIN-USER-LIST（管理：利用者一覧）
 */
@Controller
public class AdminUserListController {

	private final AdminUserService adminUserService;

	public AdminUserListController(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * 利用者一覧表示（初期表示・検索結果共通）。
	 * GET /admin/users
	 */
	@GetMapping("/admin/users")
	public String showUserList(@RequestParam(name = "searchUserName", required = false) String searchUserName,
			HttpSession session, Model model) {

		// 管理ログインチェック
		Boolean isAdminLogin = (Boolean) session.getAttribute("LOGIN_ADMIN");
		if (isAdminLogin == null || !isAdminLogin) {
			return "redirect:/admin/login";
		}

		List<User> users = adminUserService.searchUsers(searchUserName);

		model.addAttribute("searchUserName", searchUserName);
		model.addAttribute("userList", users);

		return "admin/admin_user_list";
	}

	/**
	 * 検索ボタン押下時の POST も同じ処理に流す。
	 */
	@PostMapping("/admin/users")
	public String searchUserList(@RequestParam(name = "searchUserName", required = false) String searchUserName,
			HttpSession session, Model model) {
		return showUserList(searchUserName, session, model);
	}
}
