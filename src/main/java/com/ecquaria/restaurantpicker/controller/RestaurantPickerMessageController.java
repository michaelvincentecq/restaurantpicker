package com.ecquaria.restaurantpicker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.ecquaria.restaurantpicker.dto.RestaurantPickerDto;
import com.ecquaria.restaurantpicker.request.SubmitRestaurantNameRequest;
import com.ecquaria.restaurantpicker.service.RpSessionService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class RestaurantPickerMessageController {

	@Autowired
	private RpSessionService rpSessionService;

	@MessageMapping("/submitRestaurantName/{sessionId}")
	@SendTo("/topic/restaurantPicker/{sessionId}")
	public RestaurantPickerDto submitRestaurantName(SubmitRestaurantNameRequest submitRestaurantNameRequest,
			@DestinationVariable String sessionId) {
		log.info("Begin | submitRestaurantName | sessionId: " + sessionId);

		RestaurantPickerDto restaurantPickerDto = new RestaurantPickerDto();

		try {
			restaurantPickerDto = rpSessionService.submitRestaurantName(submitRestaurantNameRequest, sessionId);
		} catch (IllegalArgumentException e) {
			log.error(e, e);
			restaurantPickerDto.setErrorCode("E001");
			restaurantPickerDto.setErrorMessage("Invalid Session ID");
		} catch (Exception e) {
			log.error(e, e);
			restaurantPickerDto.setErrorCode("E100");
			restaurantPickerDto.setErrorMessage("Other Technical Issues");
		} finally {
			log.info("End | submitRestaurantName");
		}

		return restaurantPickerDto;
	}

	@MessageMapping("/endSession/{sessionId}")
	@SendTo("/topic/restaurantPicker/{sessionId}")
	public RestaurantPickerDto endSession(@DestinationVariable String sessionId) {
		log.info("Begin | endSession | sessionId: " + sessionId);

		RestaurantPickerDto restaurantPickerDto = new RestaurantPickerDto();

		try {
			restaurantPickerDto = rpSessionService.endSession(sessionId);
		} catch (IllegalArgumentException e) {
			log.error(e, e);
			restaurantPickerDto.setErrorCode("E001");
			restaurantPickerDto.setErrorMessage("Invalid Session ID");
		} catch (IllegalStateException e) {
			log.error(e, e);
			restaurantPickerDto.setErrorCode("E002");
			restaurantPickerDto.setErrorMessage("No Valid Submissions");
		} catch (Exception e) {
			log.error(e, e);
			restaurantPickerDto.setErrorCode("E100");
			restaurantPickerDto.setErrorMessage("Other Technical Issues");
		} finally {
			log.info("End | endSession");
		}

		return restaurantPickerDto;
	}

}