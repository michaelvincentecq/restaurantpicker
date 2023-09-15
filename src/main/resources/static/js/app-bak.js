function getSessionIdFromUrl() {
	const urlParams = new URLSearchParams(window.location.search);
	return urlParams.get('sessionId');
}

var sessionId = getSessionIdFromUrl();

const stompClient = new StompJs.Client({
	brokerURL: 'ws://localhost:8080/restaurant-picker-websocket'
});


stompClient.onWebSocketError = (error) => {
	console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
	console.error('Broker reported error: ' + frame.headers['message']);
	console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

stompClient.onConnect = (frame) => {
	setConnected(true);
	console.log('Connected: ' + frame);
	stompClient.subscribe('/topic/restaurantPicker/' + sessionId, (restaurantPickerDto) => {
		console.log(restaurantNames.body);
		processResponse(JSON.parse(restaurantPickerDto.body));
	});
};

function createSession() {
	$.ajax({
		type: "GET",
		url: "http://localhost:8080/createSession",
		dataType: "text",
		success: function(response) {
			console.log("response: " + response);
			sessionId = response;
			connect();
		},
		error: function(xhr, status, error) {
			console.error("Error:", status, error);
			alert("Error occurred while creating a new session.");
		}
	});
}

function connect() {
	stompClient.activate();
}

function disconnect() {
	stompClient.deactivate();
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	stompClient.publish({
		destination: "/submitRestaurantName/" + sessionId,
		body: JSON.stringify({ 'name': $("#name").val() })
	});
}

function endSession() {
	stompClient.publish({
		destination: "/endSession/" + sessionId
	});
}

function processResponse(restaurantPickerDto) {
	if (restaurantPickerDto.action == "SUBMIT") {
		var restaurantNames = restaurantPickerDto.restaurantNameList;
		console.log(restaurantNames);
		for (var i = 0; i < restaurantNames.length; i++) {
			var restaurantName = "<tr><td>" + restaurantNames[i] + "</td></tr>";
			if (i == 0) {
				$("#restaurantNames").text("");
			}
			$("#restaurantNames").append(restaurantName);
		}
	} else if (restaurantPickerDto.action == "END_SESSION") {
		$("#restaurantNames").text("");
		$("#restaurantNames").append(restaurantPickerDto.selectedRestaurantName);
	}
}

$(function() {
	$("form").on('submit', (e) => e.preventDefault());
	$("#connect").click(() => connect());
	$("#createSession").click(() => createSession());
	$("#disconnect").click(() => disconnect());
	$("#send").click(() => sendName());
	$("#endSession").click(() => endSession());
});

document.addEventListener("DOMContentLoaded", function() {
	if (sessionId != null) {
		connect();
	}
});