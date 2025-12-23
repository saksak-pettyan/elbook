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

import com.kaneko.elbook.domain.Book;
import com.kaneko.elbook.form.AdminBookForm;
import com.kaneko.elbook.service.AdminBookService;

/**
 * SCR-ADMIN-BOOK-DETAIL（管理：書籍詳細・編集）のコントローラ。
 */
@Controller
public class AdminBookDetailController {

	private final AdminBookService adminBookService;

	public AdminBookDetailController(AdminBookService adminBookService) {
		this.adminBookService = adminBookService;
	}

	/**
	 * 書籍詳細・編集画面表示（新規／更新共通）
	 *
	 * GET /admin/books/detail?bookId=...
	 * bookId なし → 新規登録モード
	 * bookId あり → 更新モード
	 */
	@GetMapping("/admin/books/detail")
	public String showAdminBookDetail(
			@RequestParam(name = "bookId", required = false) Long bookId,
			HttpSession session,
			Model model) {

		AdminBookForm form = new AdminBookForm();

		if (bookId == null) {
			// 新規登録
			form.setStockCount(0);
			form.setRentalCount(0);
			model.addAttribute("mode", "create");
		} else {
			// 更新モード：DB から取得
			Book book = adminBookService.getBook(bookId);
			if (book == null) {
				// 存在しない ID の場合はいったん一覧に戻す
				return "redirect:/admin/books";
			}

			form.setBookId(book.getBookId());
			form.setBookName(book.getTitle());
			form.setAuthorName(book.getAuthor());
			form.setLocation(book.getLocation());
			form.setTotalCount(book.getTotalCount());
			form.setStockCount(book.getStockCount());
			form.setRentalCount(book.getRentalCount());

			model.addAttribute("mode", "update");
		}

		model.addAttribute("adminBookForm", form);
		return "admin/admin_book_detail";
	}

	/**
	 * 書籍登録・更新処理（新規／更新共通）
	 *
	 * POST /admin/books/detail
	 */
	@PostMapping("/admin/books/detail")
	public String submitAdminBookDetail(
			@Valid AdminBookForm adminBookForm,
			BindingResult bindingResult,
			HttpSession session,
			Model model,
			RedirectAttributes redirectAttributes) {

	
		boolean isUpdate = (adminBookForm.getBookId() != null);

		// 整合性チェック：総冊数 >= 貸出冊数（更新時のみ）
		if (!bindingResult.hasErrors() && isUpdate) {
			Integer total = adminBookForm.getTotalCount();
			Integer rental = adminBookForm.getRentalCount();
			if (total != null && rental != null && total < rental) {
				bindingResult.rejectValue("totalCount", "totalCount.tooSmall",
						"総冊数は現在の貸出冊数以上でなければなりません。");
			}
		}

		// エラーがあれば画面再表示
		if (bindingResult.hasErrors()) {
			model.addAttribute("mode", isUpdate ? "update" : "create");
			return "admin/admin_book_detail";
		}

		// 正常時の登録／更新処理
		if (isUpdate) {
			adminBookService.updateBook(adminBookForm);
			redirectAttributes.addFlashAttribute("message", "書籍情報を更新しました。");
		} else {
			// 新記事は貸出冊数 = 0、在庫数 = 総冊数扱い
			adminBookForm.setRentalCount(0);
			adminBookForm.setStockCount(adminBookForm.getTotalCount());
			adminBookService.registerNewBook(adminBookForm);
			redirectAttributes.addFlashAttribute("message", "書籍情報と登録しました。");
		}

		// 一覧へ戻る
		return "redirect:/admin/books";
	}
}
