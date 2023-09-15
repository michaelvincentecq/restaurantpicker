package com.ecquaria.restaurantpicker.response;

import java.util.List;

import lombok.Data;

@Data
public class JoinSessionResponse {
	private String sessionId;
	private List<String> restaurantNames;
	private String errorCode;
	private String errorMessage;

}
