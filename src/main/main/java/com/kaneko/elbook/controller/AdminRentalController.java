package com.kaneko.elbook.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kaneko.elbook.dto.AdminRentalRow;
import com.kaneko.elbook.form.AdminRentalHistoryForm;
import com.kaneko.elbook.form.AdminRentalListForm;
import com.kaneko.elbook.service.AdminRentalService;

@Controller
public class AdminRentalController {

	private final AdminRentalService adminRentalService;

	public AdminRentalController(AdminRentalService adminRentalService) {
		this.adminRentalService = adminRentalService;
	}

	@GetMapping("/admin/rentals/current")
	public String showCurrent(@ModelAttribute("form") AdminRentalListForm form,
			HttpSession session,
			Model model) {

		List<AdminRentalRow> list = adminRentalService.findCurrentRentals(form);
		model.addAttribute("list", list);
		return "admin/admin_rental_list";
	}

	@PostMapping("/admin/rentals/current")
	public String searchCurrent(@ModelAttribute("form") AdminRentalListForm form,
			@RequestParam(value = "action", required = false) String action,
			HttpSession session,
			Model model) {

		if ("clear".equals(action)) {
			return "redirect:/admin/rentals/current";
		}

		List<AdminRentalRow> list = adminRentalService.findCurrentRentals(form);
		model.addAttribute("list", list);
		return "admin/admin_rental_list";
	}

	@GetMapping("/admin/rentals/history")
	public String showHistory(@ModelAttribute("form") AdminRentalHistoryForm form,
			HttpSession session,
			Model model) {

		List<AdminRentalRow> list = adminRentalService.findRentalHistory(form);
		model.addAttribute("list", list);
		return "admin/admin_rental_history";
	}

	@PostMapping("/admin/rentals/history")
	public String searchHistory(@ModelAttribute("form") AdminRentalHistoryForm form,
			@RequestParam(value = "action", required = false) String action,
			HttpSession session,
			Model model) {

		if ("clear".equals(action)) {
			return "redirect:/admin/rentals/history";
		}

		List<AdminRentalRow> list = adminRentalService.findRentalHistory(form);
		model.addAttribute("list", list);
		return "admin/admin_rental_history";
	}
}
