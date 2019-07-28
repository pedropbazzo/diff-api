# diff-api

[![Build Status](https://travis-ci.org/mariazevedo88/diff-api.svg?branch=master)](https://travis-ci.org/mariazevedo88/diff-api) [![Coverage Status](https://coveralls.io/repos/github/mariazevedo88/diff-api/badge.svg?branch=master)](https://coveralls.io/github/mariazevedo88/diff-api?branch=master)

### About

Diff API that accepts and compare JSON base64 strings.

### API endpoints and documentation

This API provides HTTP endpoints that accepts JSON base64 encoded binary data on both, as it follows:

* <b>List all messages created:</b> `<host>/`
	 
* <b>List all left messages created:</b> `<host>/v1/diff/left/all`
	 
* <b>List all right messages created:</b> `<host>/v1/diff/right/all`

* <b>Clean all messages created:</b> `<host>/v1/diff/cleanAllMessages`

* <b>Encode some string in a JSON base64 string on the left endpoint:</b> `<host>/v1/diff/{id}/left/encodeString`
	 
* <b>Encode some string some JSON base64 string on the right endpoint:</b> `<host>/v1/diff/{id}/right/encodeString`
	 
* <b>Decode some JSON base64 string on the left endpoint:</b> `<host>/v1/diff/{id}/left/decodeString`
	 
* <b>Decode some JSON base64 string on the right endpoint:</b> `<host>/v1/diff/{id}/right/decodeString`

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
    Eclipse 2019-06
    Spring Boot 2.1.4  
    
### Compile and Package

The API also was developed to run with an jar. In order to generate this jar, you should run:

```
mvn package
```

It will clean, compile and generate a jar at target directory, e.g. `diff-api-1.0.0.jar`

### Test

To run the API Unit and Unit and Integration Tests, follow the instructions below:

* For both test phases, you can run:

```
mvn test
```

* To run only Unit Tests:

```
mvn -Dtest=DiffApiApplicationUnitTests test
```

* To run only Integration Tests:

```
mvn -Dtest=DiffApiApplicationIntegrationTests test
```
    
### Run

In order to run the API, run the jar simply as following:

```
java -jar diff-api-1.0.0.jar --spring.profiles.active=prod
```
    
or

```
mvn spring-boot:run -Dspring.profiles.active=prod
```

By default, the API will be available at [http://localhost:8080/](http://localhost:8080/)