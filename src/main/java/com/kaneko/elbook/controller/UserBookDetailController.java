package com.kaneko.elbook.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.service.RentalService;
import com.kaneko.elbook.service.UserBookService;

/**
 * 利用者側：書籍詳細画面（SCR-USER-BOOK-DETAIL 相当）
 */
@Controller
public class UserBookDetailController {

	private final UserBookService userBookService;
	private final RentalService rentalService;

	public UserBookDetailController(UserBookService userBookService, RentalService rentalService) {
		this.userBookService = userBookService;
		this.rentalService = rentalService;
	}

	/**
	 * 書籍詳細画面の表示
	 *
	 * GET /user/books/detail?bookId=...
	 */
	@GetMapping("/user/books/detail")
	public String showUserBookDetail(@RequestParam("bookId") Long bookId, HttpSession session, Model model) {

		// 1. 一般利用者ログインチェック
		User loginUser = (User) session.getAttribute("LOGIN_USER");
		if (loginUser == null) {
		    return "redirect:/user/login";
		}

		// 2. 書籍情報を取得
		Book book = userBookService.getBook(bookId);
		if (book == null) {
			// 指定IDの書籍が存在しない場合はいったん閲覧へ戻す
			return "redirect:/user/books";
		}

		// 3. 画面に渡す
		model.addAttribute("book", book);

		// 4. 利用者用の書籍詳細画面を表示
		return "user/user_book_detail";
	}

	/**
	 * 「この本を借りる」ボタン押下時の処理
	 */
	@PostMapping("/user/books/borrow")
	public String borrowBook(@RequestParam("bookId") Long bookId,
			HttpSession session,
			RedirectAttributes redirectAttributes) {

		User loginUser = (User) session.getAttribute("LOGIN_USER");
		if (loginUser == null) {
			return "redirect:/user/login";
		}

		boolean success = rentalService.rentBook(bookId, loginUser.getUserId());

		if (success) {
			redirectAttributes.addFlashAttribute("message", "貸出手続きが完了しました。");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "在庫がないため貸出できません。");
		}

		return "redirect:/user/books/detail?bookId=" + bookId;
	}

}
