## Flight Search API
SpringBoot Application that demonstrates REST API Development using Spring MVC, Spring Data JPA using Java 8 features.

### 1. GET /v1/flights?sortBy=fare
  ### Details:
      This API will dispaly all flights and sort by fare,origin,destination,arrival,departure...
      
### 2. GET /v1/flights/{origin}/{destination}?sortBy=fare&sortType=asc
  ### Details:
      This API will dispaly all flights based on mentioned origin and destination as well as sort results by fare,arrival,departure 
			with ascending and descending order.

### Prerequisites
1. Java 8
2. Maven 3
3. Git

### Technologies used :
 - 1.Java (Programming Language)
 - 2.Spring Boot (Application Platform)
 - 3.Spring Data JPA (Data persistence)
 - 4.H2 (Database)
 - 5.JUnit5, with Spring Testing (Unit & Integration Testing)

### Installing & Running

#### Clone this repo into your local:
```
git clone https://sameerean@bitbucket.org/sameerean/flight-booking.git
```

####  Build using maven
```
mvn clean install
```

#### Start the app
```
mvn spring-boot:run
```

## Database
This application is using H2 in-memory database, which (database as well as data) will be removed from memory when the application goes down.
While the application is running, you can access the [H2 Console](http://localhost:8085/console) if you want to see the data outside the application.
You can connect to the DB using the JDBC URL: 'jdbc:h2:mem:flight-booking' and user 'minal' with empty password. 
