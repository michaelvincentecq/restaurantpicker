package com.ecquaria.restaurantpicker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecquaria.restaurantpicker.dto.RestaurantPickerDto;
import com.ecquaria.restaurantpicker.enums.RpMessageTypeEnum;
import com.ecquaria.restaurantpicker.enums.RpSessionStatusEnum;
import com.ecquaria.restaurantpicker.model.RpSession;
import com.ecquaria.restaurantpicker.model.RpSubmission;
import com.ecquaria.restaurantpicker.repository.RpSessionRepository;
import com.ecquaria.restaurantpicker.request.SubmitRestaurantNameRequest;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RpSessionService {

	private Random random = new Random();

	@Autowired
	private RpSessionRepository rpSessionRepository;

	@Transactional
	public RestaurantPickerDto submitRestaurantName(SubmitRestaurantNameRequest submitRestaurantNameRequest,
			String sessionId) {
		log.info("Begin | submitRestaurantName");

		RestaurantPickerDto restaurantPickerDto = new RestaurantPickerDto(RpMessageTypeEnum.SUBMIT);

		try {
			Optional<RpSession> optionalRpSession = rpSessionRepository.findBySessionUuid(UUID.fromString(sessionId));

			if (optionalRpSession.isPresent()) {
				RpSession rpSession = optionalRpSession.get();
				log.info("Added Restaurant Name: " + submitRestaurantNameRequest.getRestaurantName());

				// If the rpSubmissions field is null, create a new ArrayList to store the
				// submission
				if (rpSession.getRpSubmissions() == null) {
					rpSession.setRpSubmissions(new ArrayList<>());
				}

				// Add new RpSubmission entry into the RpSession entity
				rpSession.getRpSubmissions().add(new RpSubmission(rpSession,
						submitRestaurantNameRequest.getRestaurantName(), submitRestaurantNameRequest.getUsername()));

				// Save into the DB
				rpSessionRepository.save(rpSession);

				// Collect all restaurant names to be sent back to the users
				List<String> restaurantNames = rpSession.getRpSubmissions().stream()
						.map(rpSubmission -> rpSubmission.getRestaurantName()).collect(Collectors.toList());

				restaurantPickerDto.setRestaurantNameList(restaurantNames);
			} else {
				throw new IllegalArgumentException("Invalid Session ID");
			}
		} catch (Exception e) {
			log.error(e, e);
			throw e;
		} finally {
			log.info("End | submitRestaurantName");
		}

		return restaurantPickerDto;

	}

	@Transactional
	public RestaurantPickerDto endSession(String sessionId) {
		log.info("Begin | endSession");

		RestaurantPickerDto restaurantPickerDto = new RestaurantPickerDto(RpMessageTypeEnum.END_SESSION);

		try {
			Optional<RpSession> optionalRpSession = rpSessionRepository.findBySessionUuid(UUID.fromString(sessionId));

			if (optionalRpSession.isPresent()) {
				RpSession rpSession = optionalRpSession.get();

				if (rpSession.getRpSubmissions().isEmpty()) {
					throw new IllegalStateException("NO_VALID_SUBMISSION");
				} else {
					List<String> restaurantNames = rpSession.getRpSubmissions().stream()
							.map(rpSubmission -> rpSubmission.getRestaurantName()).collect(Collectors.toList());
					
					String randomRestaurantName = restaurantNames.get(random.nextInt(restaurantNames.size()));
					
					log.info("Randomly Selected Restaurant Name: " + randomRestaurantName);
					
					restaurantPickerDto.setSelectedRestaurantName(randomRestaurantName);
					
					rpSession.setStatus(RpSessionStatusEnum.ENDED);
					
					rpSessionRepository.save(rpSession);
				}
			} else {
				throw new IllegalArgumentException("INVALID_SESSION_ID");
			}
		} catch (Exception e) {
			log.error(e, e);
			throw e;
		} finally {
			log.info("End | endSession");
		}

		return restaurantPickerDto;

	}

}
