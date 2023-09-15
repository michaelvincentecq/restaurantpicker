# Restaurant Picker

Restaurant Picker is a web-based application designed to make dining decisions easier for groups of people.
Whether you're planning a lunch outing with colleagues, a dinner with friends, or any group dining experience,
Restaurant Picker simplifies the decision-making process.

## Key Features

- **Create Sessions**: Start a new session and invite your friends or colleagues to join.

- **Add Restaurants**: Build a list of restaurant options that you're considering.

- **Random Selection**: When it's time to decide where to eat, Restaurant Picker randomly selects a restaurant from your list.

- **Real-Time Updates**: Seamless experience with real-time updates powered by WebSocket technology, ensuring everyone sees the same list and result.

## Dependencies

To run this Spring Boot application, you'll need Java 17 and Maven installed on your system. Follow these steps to get started:

### 1. Install Java 17

If you don't have Java 17 installed, you can download and install it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-downloads.html) or use an OpenJDK distribution.

To check if Java 17 is already installed, open a terminal and run:

```bash
java -version
```

You should see output indicating that Java 17 is installed.

### 2. Install Maven

If you don't have Maven installed, you can download and install it from the [official Maven website](https://maven.apache.org/download.cgi).

To check if Maven is already installed, open a terminal and run:

```bash
mvn -version
```

You should see output showing the installed Maven version.

### 3. Clone the Repository

Clone this GitHub repository to your local machine using the following command:

```bash
git clone https://github.com/michaelvincentecq/restaurantpicker.git
```

### 4. Build the Application

Navigate to the project's root directory:

```bash
cd /path/to/restaurantpicker
```

Build the application using Maven:

```bash
mvn clean install
```

### 5. Run the Application

Once the build is successful, you can run the Spring Boot application using the following command:

```bash
mvn spring-boot:run
```

The application will start, and you should see output in the terminal indicating that the application is running.

### 6. Access the Application

Open your web browser and access the application at [http://localhost:8001](http://localhost:8001).
![1](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/eb3d8b68-a898-4013-be73-e4f932108be7)

## Functionalities

### Creating a session

* To initiate  a new session, a user must click on CREATE SESSION button ![2](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/568c7563-f526-4fa8-b5eb-2d118c8b6682)
* Once a new session has been created, the Restaurant Picker will be displayed
![3](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/4a60bc16-df13-4d67-bf17-649ab3fa7bf7)



### Inviting other users to join the session

* From the Restaurant Picker screen, any user can click on SHARE SESSION button ![4](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/456a5d48-3449-4beb-9b6d-48128e68dc3c)
* The screen will display a 4 letter code that the users can share to their friends to join the picking session.
![5](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/520ea8c0-7faf-4c82-9016-87a050f65d96)

### Joining a session

* After a user receives the 4 letter session code, they can input it into the text box in the initial screen then click on JOIN SESSION button.
![6](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/1fa411d2-d1d9-4db0-8689-96ff873e2f5e)

* Once successfully joined, the Restaurant Picker will be displayed and they can start to submit their choice.
![7](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/1f111fd6-4d17-40ba-8602-bd7637e69822)

### Submitting a restaurant name

* In the Restaurant Picker text box, they can input the restaurant name of their choice then click on SUBMIT button.
![8](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/59bc3315-40c2-4cc2-8ef0-2b0bdf5e7027)

* The submitted restaurant name will be listed in the text area and be displayed on other user's screen. Also the submission from other users will be displayed in the screen real time.

### Randomly select a restaurant from the list (Ending a session)

* Once the group are ready for the random selection, the initiator can end the session by clicking on END SESSION button. ![9](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/30e6d636-b63f-452b-87b9-cfd21ae00797)
* The application will randomly select from the list and display into each user's screen in the session.
![10](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/55001be4-ce60-441d-847e-6e85252acf60)

## Rest APIs

### Create Session
```
End Point: /createSession
Parameter: None
Response: JSON
Sample Response: {"sessionId":"8ee233c7-8cf9-4465-aad3-6e721dd22db5","sessionCode":"OXYF"}
```

### Join Session
```
End Point: /joinSession
Parameter: sessionCode
Response: JSON
Sample Response: {"sessionId":8ee233c7-8cf9-4465-aad3-6e721dd22db5,"restaurantNames":null,"errorCode":null,"errorMessage":null}
```

## Message Topic

### Receiving message from the server
```
Topic: /topic/restaurantPicker/{sessionId}
```

Whenever there are response from the server, the subscriber of this topic will receive based on the sessionId.

## Message Destinations

### Submit Restaurant Name
```
Destination: /submitRestaurantName/{sessionId}
Parameter: submitRestaurantNameMessage.restaurantName
Java Controller: RestaurantPickerMessageController.submitRestaurantName
```

### End Session
```
Destination: /endSession/{sessionId}
Parameter: None
Java Controller: RestaurantPickerMessageController.endSession
```

## Technologies Used

* Spring Boot
* Maven
* Java 17
* JQuery
* Websocket
* H2 Database
* HTML/CSS
* Javascript
