# Flight Search API
SpringBoot Application that demonstrates REST API Development using Spring MVC, Spring Data JPA using Java 8 features.

1. GET /v1/flights?sortBy=fare
  # Details:
      This API will dispaly all flights and sort by fare,origin,destination,arrival,departure...
      
2. GET /v1/flights/{origin}/{destination}?sortBy=fare&sortType=asc
  # Details:
      This API will dispaly all flights based on mentioned origin and destination as well as sort results by fare,arrival,departure 
			with ascending and descending order.
      
# Technologies used :
1.Java (Programming Language)
2.Spring Boot (Application Platform)
3.Spring Data JPA (Data persistence)
4.H2 (Database)
5.JUnit5, with Spring Testing (Unit & Integration Testing)
