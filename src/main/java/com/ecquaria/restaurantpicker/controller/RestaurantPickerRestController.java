package com.ecquaria.restaurantpicker.controller;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecquaria.restaurantpicker.enums.RpSessionStatusEnum;
import com.ecquaria.restaurantpicker.model.RpSession;
import com.ecquaria.restaurantpicker.repository.RpSessionRepository;
import com.ecquaria.restaurantpicker.response.CreateSessionResponse;
import com.ecquaria.restaurantpicker.response.JoinSessionResponse;
import com.ecquaria.restaurantpicker.util.RestaurantPickerUtil;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class RestaurantPickerRestController {

	@Autowired
	private RpSessionRepository rpSessionRepository;

	@GetMapping("/createSession")
	public CreateSessionResponse createSession(String username) {
		log.info("Begin | createSession");

		CreateSessionResponse createSessionResponse = new CreateSessionResponse();

		try {
			RpSession rpSession = new RpSession();

			String sessionCode = RestaurantPickerUtil.generateSessionCode();

			while (rpSessionRepository.countBySessionCodeAndStatus(sessionCode, RpSessionStatusEnum.ACTIVE) > 0) {
				sessionCode = RestaurantPickerUtil.generateSessionCode();
			}

			rpSession.setSessionCode(sessionCode);
			rpSession.setStatus(RpSessionStatusEnum.ACTIVE);
			rpSession.setCreatedBy(username);
			rpSession.setCreatedDate(new Date());

			rpSessionRepository.save(rpSession);

			createSessionResponse.setSessionId(rpSession.getSessionUuid().toString());
			createSessionResponse.setSessionCode(sessionCode);
		} catch (Exception e) {
			log.error(e, e);
		} finally {
			log.info("End | createSession | sessionId: " + createSessionResponse.getSessionId());
		}

		return createSessionResponse;
	}

	@GetMapping("/joinSession")
	public JoinSessionResponse joinSession(String sessionCode) {
		log.info("Begin | joinSession");

		JoinSessionResponse joinSessionResponse = new JoinSessionResponse();

		try {
			Optional<RpSession> optionalRpSession = rpSessionRepository.findBySessionCodeAndStatus(sessionCode,
					RpSessionStatusEnum.ACTIVE);

			if (optionalRpSession.isPresent()) {
				RpSession rpSession = optionalRpSession.get();
				joinSessionResponse.setRestaurantNames(rpSession.getRpSubmissions().stream()
						.map(rpSubmission -> rpSubmission.getRestaurantName()).collect(Collectors.toList()));
				joinSessionResponse.setSessionId(rpSession.getSessionUuid().toString());
			} else {
				joinSessionResponse.setErrorCode("E002");
				joinSessionResponse.setErrorMessage("Invalid session ID or session has been ended");
			}
		} catch (Exception e) {
			joinSessionResponse.setErrorCode("E100");
			joinSessionResponse.setErrorMessage("Other Technical Issues");
			log.error(e, e);
		} finally {
			log.info("End | joinSession");
		}

		return joinSessionResponse;
	}

}