# diff-api

[![Build Status](https://travis-ci.org/mariazevedo88/diff-api.svg?branch=master)](https://travis-ci.org/mariazevedo88/diff-api) [![Coverage Status](https://coveralls.io/repos/github/mariazevedo88/diff-api/badge.svg?branch=master)](https://coveralls.io/github/mariazevedo88/diff-api?branch=master) [![Known Vulnerabilities](https://snyk.io//test/github/mariazevedo88/diff-api/badge.svg?targetFile=pom.xml)](https://snyk.io//test/github/mariazevedo88/diff-api?targetFile=pom.xml)

### About

Diff API that accepts and compare JSON base64 strings.

### API endpoints and documentation

This API provides HTTP endpoints that accepts JSON base64 encoded binary data on both, as it follows:

* <b>List all messages created:</b> `<host>/v1/diff/all`
	 
* <b>Create a message on the left endpoint:</b> `<host>/v1/diff/<ID>/left`
        
* <b>Create a message on the right endpoint:</b> `<host>/v1/diff/<ID>/right`

* <b>The provided data is diff-ed and the results are available on a third end point:</b> `<host>/v1/diff/<ID>`

The API documentation could be found here: [https://diffapi.docs.apiary.io/#](https://diffapi.docs.apiary.io/#)

<b>The results are provided in JSON format as following:</b>

    If equal return that
    If not of equal size just return that
    If of same size provide insight in where the diffs are, actual diffs are not needed.
        § So mainly offsets + length in the data

### Technologies

This project was developed with:

    Java Development Kit (JDK) 1.8.0_212
    JUnit 5.3.2
    Apache Maven 3.6.1
    Eclipse 2019-12
    Spring Boot 2.2.5
    PostgreSQL 9  
    Flyway
    
### Compile and Package

The API also was developed to run with an jar. In order to generate this jar, you should run:

```
mvn package
```

It will clean, compile and generate a jar at target directory, e.g. `diff-api-1.1.0.jar`

### Execution

You need to have PostgreSQL 9 installed on your machine to run the API on `dev` profile. After installed, on the pgAdmin create a database named `diff`. If you don't have pdAdmin installed you can run on the psql console the follow command:

```
CREATE database diff;
```

When the application is running Flyway will create the necessary tables for the creation of the words and the execution of the compare between the endpoints.

In the `test` profile, the application uses H2 databse (data in memory).

### Test

To run the API Unit and Unit and Integration Tests, follow the instructions below:

* To run only Unit Tests:

```
mvn test
```

* To run all tests (including Integration Tests):

```
mvn integration-test
```
    
### Run

In order to run the API, run the jar simply as following:

```
java -jar diff-api-1.1.0.jar --spring.profiles.active=dev
```
    
or

```
mvn spring-boot:run
```

By default, the API will be available at [http://localhost:8080/](http://localhost:8080/)

### Documentation

* Apiary: https://diffapi.docs.apiary.io/#
* Swagger (development environment): http://localhost:8080/v2/api-docs

### Contributors

[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/0)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/0)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/1)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/1)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/2)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/2)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/3)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/3)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/4)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/4)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/5)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/5)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/6)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/6)[![](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/images/7)](https://sourcerer.io/fame/mariazevedo88/mariazevedo88/diff-api/links/7)
