package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AdminMenuController {
	
    /**
	 * 管理メニュー表示(SCR-ADMIN-MENU)
	 */
    @GetMapping("/admin/menu")
    public String showAdminMenu(HttpSession session) {
        
    	// 1. 管理者ログイン済みか簡易チェック
        Boolean isAdminLogin = (Boolean) session.getAttribute("LOGIN_ADMIN");
        if (isAdminLogin == null || !isAdminLogin) {
            // 未ログインなら管理ログインへ戻す
            return "redirect:/admin/login";
        }
        // 2. 管理メニュー画面を表示
        return "admin/admin_menu";
    }
    @PostMapping("/admin/logout")
    public String logout(HttpSession session) {
    	
    	// セッション破棄
    	session.invalidate();
    	
    	// ログイン選択画面へ戻る
    	return "redirect:/user/login";
    }
}
