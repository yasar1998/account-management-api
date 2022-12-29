# Account Management API
## Project
### Description
- All customers or one specific customer can be requested
- If customer exists, new account can be created based on the request (customer id and initial amount)
  - if amount is zero, the account will be created without transaction since there is no any deposit upon creation of the account
  - if amount is more than zero, the account will be created with transaction and type of transaction will be initial type which shows the initial deposit by the customer

### Validation
- Customer Id must not be blank and Initial amount must not be negative

## Test and Run with Maven
1. To run the project: *mvn spring-boot:run*
2. To run the tests: *mvn clean test*


## Technologies
- Spring Boot
- Spring Web (REST)
- Spring Data JPA
- Hibernate Validator
- Jackson
- Unit Tests with JUnit and Mockito
- Integration Tests with MockMvc
- Docker (Docker-compose)
- PostgreSQL database


### REST endpoints
- REST endpoints can be reviewed with Postman.

### Testing
- Unit tests with JUnit and Mockito have been provided
- Integration tests with MockMvc have been provided
