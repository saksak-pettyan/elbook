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
    public String showAdminMenu() {
        
        // 管理メニュー画面を表示
        return "admin/admin_menu";
    }
    @PostMapping("/admin/logout")
    public String logout(HttpSession session) {
    	
    	// セッション破棄
    	session.invalidate();
    	
    	// ログイン選択画面へ戻る
    	return "redirect:/admin/login";
    }
}
