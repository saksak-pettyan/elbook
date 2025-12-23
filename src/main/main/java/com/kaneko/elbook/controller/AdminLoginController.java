package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.kaneko.elbook.form.AdminLoginForm;
import com.kaneko.elbook.service.AdminAccountService;

@Controller
public class AdminLoginController {

    private final AdminAccountService adminAccountService;

    public AdminLoginController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    @GetMapping("/admin/login")
    public String showAdminLogin(AdminLoginForm form) {
        return "admin/admin_login";
    }

    @PostMapping("/admin/login")
    public String doAdminLogin(
            @Valid AdminLoginForm form,
            BindingResult bindingResult,
            Model model,
            HttpSession session) {

        // ① 入力チェック
        if (bindingResult.hasErrors()) {
            return "admin/admin_login";
        }

        // ② パスワード認証
        boolean success = adminAccountService.authenticateAdmin(form.getAdminPassword());
        if (!success) {
            model.addAttribute("errorMessage", "パスワードが正しくありません。");
            return "admin/admin_login";
        }

        // ③ 認証成功 → セッションにフラグセット
        session.setAttribute("LOGIN_ADMIN", true);

        // ④ 管理メニューへリダイレクト
        return "redirect:/admin/menu";
    }
}
