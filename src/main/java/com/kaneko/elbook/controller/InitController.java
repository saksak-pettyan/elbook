package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaneko.elbook.service.AdminAccountService;

@Controller
@RequestMapping("/init")
public class InitController {

    private final AdminAccountService adminAccountService;

    public InitController(AdminAccountService adminAccountService) {
        this.adminAccountService = adminAccountService;
    }

    /**
     * 初期設定画面表示（GET /init）
     */
    @GetMapping
    public String showInitForm(Model model) {
        // すでに初期設定済みならログイン選択画面へ
        if (adminAccountService.isInitialized()) {
            return "redirect:/user/login";
        }

        // 初回表示時はエラーメッセージなし
        model.addAttribute("error", null);
        return "init";
    }

    /**
     * 初期設定登録処理（POST /init）
     */
    @PostMapping
    public String doInit(
            @RequestParam("adminPassword") String adminPassword,
            @RequestParam("adminPasswordConfirm") String adminPasswordConfirm,
            Model model,
            HttpSession session
    ) {
        // 二重送信などで、すでに初期設定が終わっていたらログイン選択へ
        if (adminAccountService.isInitialized()) {
            return "redirect:/user/login";
        }

        // 1. 必須チェック
        if (adminPassword == null || adminPassword.isBlank()
                || adminPasswordConfirm == null || adminPasswordConfirm.isBlank()) {

            model.addAttribute("error", "パスワードを入力してください。");
            return "init";
        }

        // 2. 一致チェック
        if (!adminPassword.equals(adminPasswordConfirm)) {
            model.addAttribute("error", "パスワードが一致しません。");
            return "init";
        }

        // （必要なら 3. 桁数・形式チェックなどもここで追加可能）

        // 4. パスワード登録（ハッシュ化＋初期設定フラグ ON）
        adminAccountService.initializeAdminPassword(adminPassword);

        // 5. セッションリセット（念のため）
        session.invalidate();

        // 6. 初期設定完了後はログイン選択画面へ
        return "redirect:/user/login";
    }
}
