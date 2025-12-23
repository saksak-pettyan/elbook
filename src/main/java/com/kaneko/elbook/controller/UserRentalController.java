package com.kaneko.elbook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kaneko.elbook.domain.User;
import com.kaneko.elbook.dto.UserRentalRow;
import com.kaneko.elbook.service.RentalService;

@Controller
public class UserRentalController {

	private final RentalService rentalService;

	public UserRentalController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	/**自分の貸出中一覧 */
	@GetMapping("/user/rentals/current")
	public String showMyCurrent(@SessionAttribute("LOGIN_USER") User loginUser, Model model) {
		List<UserRentalRow> list = rentalService.getMyCurrentRentals(loginUser.getUserId());
		model.addAttribute("rentalList", list);
		return "user/user_my_rental";
	}

	/** 自分の貸出履歴 */
	@GetMapping("/user/rentals/history")
	public String showMyHistory(@SessionAttribute("LOGIN_USER") User loginUser, Model model) {
		List<UserRentalRow> list = rentalService.getMyRentalHistory(loginUser.getUserId());
		model.addAttribute("rentalList", list);
		return "user/user_my_rental_history";
	}

	/** 返却（貸出中一覧から押下） */
	@PostMapping("/user/rentals/return")
	public String returnBook(@RequestParam("rentalId") Long rentalId, @SessionAttribute("LOGIN_USER") User loginUser,
			RedirectAttributes ra) {

		boolean success = rentalService.returnBook(rentalId, loginUser.getUserId());
		if (success) {
			ra.addFlashAttribute("message", "返却しました。");
		} else {
			ra.addFlashAttribute("errorMessage", "返却できませんでした（対象が存在しない可能性があります）。");
		}
		return "redirect:/user/rentals/current";
	}
}
