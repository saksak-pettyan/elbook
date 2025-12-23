package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.form.AdminUserForm;
import com.kaneko.elbook.service.AdminUserService;

/**
 * SCR-ADMIN-USER-DETAIL（管理：利用者詳細）
 */
@Controller
public class AdminUserDetailController {

	private final AdminUserService adminUserService;

	public AdminUserDetailController(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	/**
	 * 利用者詳細表示（新規／更新共通）。
	 * GET /admin/users/detail?userId=...
	 */
	@GetMapping("/admin/users/detail")
	public String showUserDetail(@RequestParam(name = "userId", required = false) Long userId, HttpSession session,
			Model model) {

		AdminUserForm form = new AdminUserForm();

		if (userId == null) {
			// 新規登録モード
			form.setStatus("有効");
			model.addAttribute("mode", "create");
		} else {
			// 更新モード：DB から取得
			User user = adminUserService.getUser(userId);
			if (user == null) {
				// 存在しないIDなら一覧に戻す
				return "redirect:/admin/users";
			}

			form.setUserId(user.getUserId());
			form.setUserName(user.getUserName());
			form.setLoginId(user.getLoginId());
			form.setStatus(user.getStatus());
			form.setCreatedAt(user.getCreatedAt());
			form.setUpdatedAt(user.getUpdatedAt());

			model.addAttribute("mode", "update");
		}

		model.addAttribute("adminUserForm", form);
		return "admin/admin_user_detail";
	}

	/**
	 * 利用者登録・更新処理。
	 * POST /admin/users/detail
	 */
	@PostMapping("/admin/users/detail")
	public String submitUserDetail(@Valid AdminUserForm adminUserForm, BindingResult bindingResult, HttpSession session,
			Model model, RedirectAttributes redirectAttributes) {

		boolean isUpdate = (adminUserForm.getUserId() != null);

		// 新規登録時のみ、パスワード必須（仕様補完としての判断）
		if (!isUpdate) {
			if (adminUserForm.getNewPassword() == null || adminUserForm.getNewPassword().isEmpty()) {
				bindingResult.rejectValue("newPassword", "newPassword.required", "初期パスワードを入力してください。");
			}
		}

		// パスワード一致チェック（再設定・新規共通の基本チェックとして補完）
		if (adminUserForm.getNewPassword() != null && !adminUserForm.getNewPassword().isEmpty()) {
			if (!adminUserForm.getNewPassword().equals(adminUserForm.getNewPasswordConfirm())) {
				bindingResult.rejectValue("newPasswordConfirm", "newPasswordConfirm.mismatch", "パスワード（確認）が一致しません。");
			}
		}

		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", isUpdate ? "update" : "create");
			return "admin/admin_user_detail";
		}

		if (isUpdate) {
			adminUserForm.setNewPassword(null);
			adminUserForm.setNewPasswordConfirm(null);
			adminUserService.updateUser(adminUserForm);
			redirectAttributes.addFlashAttribute("message", "利用者情報を更新しました。");
		} else {
			adminUserService.registerNewUser(adminUserForm);
			redirectAttributes.addFlashAttribute("message", "利用者情報を登録しました。");
		}

		return "redirect:/admin/users";
	}

	/**
	 * パスワード再設定処理（ボタン押下）。
	 * POST /admin/users/reset-password
	 */
	@PostMapping("/admin/users/reset-password")
	public String resetPassword(
			@RequestParam("userId") Long userId,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("newPasswordConfirm") String newPasswordConfirm,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		// 未入力チェック（空文字・空白のみを拒否）
		if (newPassword == null || newPassword.isBlank()) {
			redirectAttributes.addFlashAttribute("errorMessage", "パスワードを入力してください。");
			redirectAttributes.addAttribute("userId", userId);
			return "redirect:/admin/users/detail";
		}

		// 桁数チェック（Form側は @Size(max=100) ですが、RequestParamなのでここで担保）
		if (newPassword.length() > 100) {
			redirectAttributes.addFlashAttribute("errorMessage", "パスワードは100文字以内で入力してください。");
			redirectAttributes.addAttribute("userId", userId);
			return "redirect:/admin/users/detail";
		}

		// 確認一致チェック
		if (newPasswordConfirm == null || !newPassword.equals(newPasswordConfirm)) {
			redirectAttributes.addFlashAttribute("errorMessage", "パスワード（確認）が一致しません。");
			redirectAttributes.addAttribute("userId", userId);
			return "redirect:/admin/users/detail";
		}

		adminUserService.resetPassword(userId, newPassword);
		redirectAttributes.addFlashAttribute("message", "パスワードを再設定しました。");
		redirectAttributes.addAttribute("userId", userId);
		return "redirect:/admin/users/detail";
	}
}
