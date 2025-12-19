package com.kaneko.elbook.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class AdminRentalRow {
	private String userName;
    private String bookName;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private Integer rentalQuantity;
    private String status;
}
