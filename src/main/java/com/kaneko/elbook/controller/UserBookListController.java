package com.kaneko.elbook.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.service.UserBookService;

@Controller
public class UserBookListController {
	
	private final UserBookService userBookService;
	
	public UserBookListController(UserBookService userBookService) {
		this.userBookService = userBookService;
	}
	
	/** 書籍一覧表示（初期表示 & 検索） */
	@GetMapping("/user/books")
	public String showBookList(@RequestParam(name = "title",required = false)String title,HttpSession session,Model model) {
		
		// ログインチェック
		User loginUser = (User) session.getAttribute("LOGIN_USER");
		if (loginUser == null) {
		    return "redirect:/user/login";
		}
		
		// 2. 検索 or 全件取得
		List<Book> books = userBookService.searchBooks(title);
		
		// 3. 値を画面へ戻す
		model.addAttribute("title",title);
		model.addAttribute("bookList",books);
		
		return "user/user_book_list";
	}
	
	/** POST（検索）も同じ処理を使う */
	@PostMapping("/user/books")
	public String searchBookList(@RequestParam(name = "title",required = false)String title,HttpSession session,Model model) {
		return showBookList(title, session, model);

	}
}
