package com.kaneko.elbook.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.service.AdminBookService;

/**
 * SCR-ADMIN-BOOK-LIST（管理：書籍一覧）のコントローラ。
 */
@Controller
public class AdminBookListController {

	private final AdminBookService adminBookService;

	public AdminBookListController(AdminBookService adminBookService) {
		this.adminBookService = adminBookService;
	}

	/**
	 * 管理：書籍一覧表示
	 *
	 * URL：GET /admin/books
	 */
	@GetMapping("/admin/books")
	public String showAdminBookList(
			@RequestParam(name = "searchBookName", required = false) String searchBookName,
			HttpSession session,
			Model model) {


		// 1. サービスで一覧取得
		List<Book> books = adminBookService.searchBooks(searchBookName);

		// 2. 画面に渡すモデルを設定
		model.addAttribute("books", books);
		model.addAttribute("searchBookName", searchBookName);

		// 3. 一覧画面テンプレートへ
		return "admin/admin_book_list";
	}
}
