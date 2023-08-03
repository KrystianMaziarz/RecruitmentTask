# Requirements

- JVM 17

### Branches

- 'develop' for development
- 'feature/P-number' for adding features to application
- 'master' for stable and tested version


### Formatting

* 'mvn spotless:apply' to format the code
* 'mvn spotless:check' To check if the code is properly formatted

### Swagger

* url for swagger 'http://localhost:8080/swagger-ui.html'
* url for documentation of swagger 'http://localhost:8080/v3/api-docs'

### Tests
* 'mvn clean verify' to run all tests
* 'mvn clean test' run unit tests
* 'mvn failsafe:integration-test' run integration tests

### Install
* 'mvn clean install -DskipTests' install and skip all tests
* 'mvn clean install -DskipIntegrationTests' install and skip integration tests
* 'mvn clean install -DskipUnitTests' install and skip unit tests
