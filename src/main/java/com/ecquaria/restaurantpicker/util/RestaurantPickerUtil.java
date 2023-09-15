package com.ecquaria.restaurantpicker.util;

import java.util.Random;

public class RestaurantPickerUtil {

	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final int CODE_LENGTH = 4;

	private static final Random random = new Random();

	public static String generateSessionCode() {
		StringBuilder sessionCodeBuilder = new StringBuilder();

		for (int i = 0; i < CODE_LENGTH; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sessionCodeBuilder.append(randomChar);
		}

		String sessionCode = sessionCodeBuilder.toString();

		return sessionCode;
	}

}