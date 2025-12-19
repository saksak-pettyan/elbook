package com.kaneko.elbook.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserRentalRow {
	private Long rentalId;
	private Long bookId;
	private String title;
	private String author;
	private LocalDate rentalDate;
	private LocalDate returnDate;
	private Integer rentalCount;
	private String status;
}
