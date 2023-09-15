package com.ecquaria.restaurantpicker.enums;

public enum RpMessageTypeEnum {
	SUBMIT("SUBMIT"), END_SESSION("END_SESSION");

	private final String type;

	RpMessageTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}