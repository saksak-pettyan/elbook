package com.kaneko.elbook.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kaneko.elbook.dto.AdminRentalRow;
import com.kaneko.elbook.form.AdminRentalHistoryForm;
import com.kaneko.elbook.form.AdminRentalListForm;
import com.kaneko.elbook.mapper.RentalMapper;

@Service
public class AdminRentalService {

	private final RentalMapper rentalMapper;

	  public AdminRentalService(RentalMapper rentalMapper) {
	    this.rentalMapper = rentalMapper;
	  }

	public List<AdminRentalRow> findCurrentRentals(AdminRentalListForm form) {
		return rentalMapper.selectAdminCurrentRentals("貸出中", form);
	}

	public List<AdminRentalRow> findRentalHistory(AdminRentalHistoryForm form) {
		return rentalMapper.selectAdminRentalHistory(form);
	}
}
