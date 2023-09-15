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
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/a9818e44-cd50-4a1e-98a3-8819c9f1034d)

## Functionalities

### Creating a session

* To initiate  a new session, a user must click on CREATE SESSION button ![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/e423ccab-8aa8-4dc1-a985-f8c3e0b22751)
* Once a new session has been created, the Restaurant Picker will be displayed
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/58c0422e-6d9b-4278-8075-484d194399a9)


### Inviting other users to join the session

* From the Restaurant Picker screen, any user can click on SHARE SESSION button ![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/3a96425a-f20b-44dd-add4-75bf1057f700)
* The screen will display a 4 letter code that the users can share to their friends to join the picking session.
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/84270111-8170-41df-8de4-718854ac7c45)

### Joining a session

* After a user receives the 4 letter session code, they can input it into the text box in the initial screen then click on JOIN SESSION button.
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/86c7f753-63b2-41b4-a43c-87f512de8d32)

* Once successfully joined, the Restaurant Picker will be displayed and they can start to submit their choice.
  ![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/6ede6972-1053-4107-b730-ba40cfefcf21)

### Submitting a restaurant name

* In the Restaurant Picker text box, they can input the restaurant name of their choice then click on SUBMIT button.
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/98b54a71-3575-44f1-a4ae-3d48ec86d572)

* The submitted restaurant name will be listed in the text area and be displayed on other user's screen. Also the submission from other users will be displayed in the screen real time.

### Randomly select a restaurant from the list (Ending a session)

* Once the group are ready for the random selection, the initiator can end the session by clicking on END SESSION button. ![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/9dd3fce5-dd1c-4705-bb02-e93e7507644f)
* The application will randomly select from the list and display into each user's screen in the session.
![image](https://github.com/michaelvincentecq/restaurantpicker/assets/145078795/f912dbef-66be-4885-b6d5-0bfb7a119c68)

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
