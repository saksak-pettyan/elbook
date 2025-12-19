package com.kaneko.elbook.form;

import lombok.Data;

@Data
public class AdminRentalListForm {
	private String searchUserName; // 部分一致
	private String searchBookName; // 部分一致
}
