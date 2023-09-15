package com.ecquaria.restaurantpicker.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CreateSessionResponse {
	private String sessionId;
	private String sessionCode;

}
