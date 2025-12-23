package com.kaneko.elbook.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kaneko.elbook.domain.Rental;
import com.kaneko.elbook.dto.AdminRentalRow;
import com.kaneko.elbook.dto.UserRentalRow;
import com.kaneko.elbook.form.AdminRentalHistoryForm;
import com.kaneko.elbook.form.AdminRentalListForm;

@Mapper
public interface RentalMapper {

	List<AdminRentalRow> selectAdminCurrentRentals(
			@Param("statusRented") String statusRented,
			@Param("form") AdminRentalListForm form);

	List<AdminRentalRow> selectAdminRentalHistory(
			@Param("form") AdminRentalHistoryForm form);

	int insertRental(Rental rental);

	List<UserRentalRow> selectMyRentalCurrent(@Param("userId") Long userId);

	List<UserRentalRow> selectMyRentalHistory(@Param("userId") Long userId);

	int markReturned(@Param("rentalId") Long rentalId, @Param("userId") Long userId);

	Long selectBookIdByRentalIdAndUserId(@Param("rentalId") Long rentalId,
			@Param("userId") Long userId);

}