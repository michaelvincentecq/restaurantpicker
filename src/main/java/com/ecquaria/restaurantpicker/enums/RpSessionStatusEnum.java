package com.ecquaria.restaurantpicker.enums;

public enum RpSessionStatusEnum {
	ACTIVE("ACTIVE"), ENDED("ENDED");

	private final String status;

	RpSessionStatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}