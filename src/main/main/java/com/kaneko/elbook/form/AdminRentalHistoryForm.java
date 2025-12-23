package com.kaneko.elbook.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class AdminRentalHistoryForm {

	private String searchUserName;
	private String searchBookName;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate searchRentalDateFrom;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate searchRentaDateTo;

	private String searchStatus;

}
