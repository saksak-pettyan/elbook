package com.kaneko.elbook.domain;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Rental {
	private Long rentalId;
	private Long userId;
	private Long bookId;
	private LocalDate rentalDate;
	private LocalDate returnDate;
	private Integer rentalCount;
	private String status;
}
