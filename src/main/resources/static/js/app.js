'use strict';

var sessionPage = document.querySelector('#session-page');
var restaurantPickerPage = document.querySelector('#restaurant-picker-page');
var selectedNamePage = document.querySelector('#selected-name-page');
var sessionCodePage = document.querySelector('#session-code-page');
var joinSessionForm = document.querySelector('#joinSessionForm');
var submitRestaurantForm = document.querySelector('#submitRestaurantForm');
var restaurantNameInput = document.querySelector('#restaurantName');
var restaurantListArea = document.querySelector('#restaurantListArea');
var connectingElement = document.querySelector('.connecting');
var shareSessionButton = document.querySelector('#shareSessionButton');
var endSessionButton = document.querySelector('#endSessionButton');
var selectedNameH1 = document.querySelector('#selectedNameH1');
var sessionCodeH1 = document.querySelector('#sessionCodeH1');

const stompClient = new StompJs.Client({
	brokerURL: 'ws://localhost:8001/restaurant-picker-websocket'
});

var sessionCode = null;
var sessionId = null;
var initiator = false;

stompClient.onConnect = (frame) => {
	console.log('Connected: ' + frame);
	stompClient.subscribe('/topic/restaurantPicker/' + sessionId, (restaurantPickerDto) => {
		console.log(restaurantPickerDto.body);
		processResponse(JSON.parse(restaurantPickerDto.body));
	});
	sessionPage.classList.add('hidden');
	restaurantPickerPage.classList.remove('hidden');
	connectingElement.classList.add('hidden');
	if (!initiator) {
		endSessionButton.classList.add('hidden');
	}
};

function connect() {
	stompClient.activate();
}

function processResponse(restaurantPickerDto) {
	if (restaurantPickerDto.type == "SUBMIT") {
		var restaurantNames = restaurantPickerDto.restaurantNameList;
		console.log(restaurantNames);
		displayRestaurantNames(restaurantNames);
	} else if (restaurantPickerDto.type == "END_SESSION") {
		//$("#restaurantListArea").text("");
		//$("#restaurantListArea").append("Randomly Selected: " + restaurantPickerDto.selectedRestaurantName);
		selectedNamePage.classList.remove('hidden');
		selectedNameH1.innerHTML = "RANDOMLY SELECTED:<br>" + restaurantPickerDto.selectedRestaurantName;
		restaurantPickerPage.classList.add('hidden');
		stompClient.deactivate();
	} else {
		alert(restaurantPickerDto.errorMessage);
	}
}

function displayRestaurantNames(restaurantNames) {
	for (var i = 0; i < restaurantNames.length; i++) {
		var restaurantName = "<h3>" + restaurantNames[i] + "</h3>";
		if (i == 0) {
			$("#restaurantListArea").text("");
		}
		$("#restaurantListArea").append(restaurantName);
	}
	restaurantListArea.scrollTop = restaurantListArea.scrollHeight
}

function onError(error) {
	connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
	connectingElement.style.color = 'red';
}


function submitRestaurantName(event) {
	var restaurantName = restaurantNameInput.value.trim();
	if (restaurantName && stompClient) {
		var submitRestaurantNameMessage = {
			username: "",
			restaurantName: restaurantNameInput.value
		};
		stompClient.publish({
			destination: "/submitRestaurantName/" + sessionId,
			body: JSON.stringify(submitRestaurantNameMessage)
		});
		restaurantNameInput.value = '';
	}
	event.preventDefault();
}

function createSession(event) {
	console.log("createSession");
	$.ajax({
		type: "GET",
		url: "http://localhost:8001/createSession",
		dataType: "json",
		success: function(response) {
			console.log("response: " + response);
			sessionId = response.sessionId;
			sessionCode = response.sessionCode;
			initiator = true;
			connect();
		},
		error: function(xhr, status, error) {
			console.error("Error:", status, error);
			alert("Error occurred while creating a new session.");
		}
	});
	event.preventDefault();
}

function joinSession(event) {
	sessionCode = document.querySelector('#sessionCode').value.trim();
	console.log("joinSession");
	$.ajax({
		type: "GET",
		url: "http://localhost:8001/joinSession?sessionCode=" + sessionCode,
		dataType: "json",
		success: function(response) {
			console.log("response: " + response);
			sessionId = response.sessionId;
			if (sessionId != null) {
				connect();
				displayRestaurantNames(response.restaurantNames);
			} else {
				alert(response.errorMessage);
			}
		},
		error: function(xhr, status, error) {
			console.error("Error:", status, error);
			alert("Error occurred while creating a new session.");
		}
	});
	event.preventDefault();
}

function shareSession() {
	sessionCodePage.classList.remove('hidden');
	sessionCodeH1.innerHTML = "SESSION CODE:<br>" + sessionCode;
	restaurantPickerPage.classList.add('hidden');
}

function closeShareSessionPage() {
	sessionCodePage.classList.add('hidden');
	restaurantPickerPage.classList.remove('hidden');
}

function endSession() {
	if (stompClient) {
		stompClient.publish({
			destination: "/endSession/" + sessionId
		});
	}
}

joinSessionForm.addEventListener('submit', joinSession, true)
createSessionForm.addEventListener('submit', createSession, true)
submitRestaurantForm.addEventListener('submit', submitRestaurantName, true)
shareSessionButton.addEventListener('click', shareSession, true)
endSessionButton.addEventListener('click', endSession, true)
closeShareSessionPageButton.addEventListener('click', closeShareSessionPage, true)