package com.ecquaria.restaurantpicker.dto;

import java.util.List;

import com.ecquaria.restaurantpicker.enums.RpMessageTypeEnum;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantPickerDto {
	private RpMessageTypeEnum type;
	private List<String> restaurantNameList;
	private String selectedRestaurantName;
	private String sessionId;
	private String errorCode;
	private String errorMessage;

	public RestaurantPickerDto(RpMessageTypeEnum type) {
		this.type = type;
	}

}
