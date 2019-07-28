# diff-api
This API provides HTTP endpoints that accepts JSON base64 encoded binary data on both, as it follows:

* <b>List all messages created:</b> `<host>/`
	 
* <b>List all left messages created:</b> `<host>/v1/diff/left/all`
	 
* <b>List all right messages created:</b> `<host>/v1/diff/right/all`
	 
* <b>Decode some JSON base64 string on the left endpoint:</b> `<host>/v1/diff/{id}/left/decodeString`
	 
* <b>Decode some JSON base64 string on the right endpoint:</b> `<host>/v1/diff/{id}/right/decodeString`

* <b>Create a message on the left endpoint:</b> `<host>/v1/diff/<ID>/left`
        
* <b>Create a message on the right endpoint:</b> `<host>/v1/diff/<ID>/right`

* <b>The provided data is diff-ed and the results are available on a third end point:</b> `<host>/v1/diff/<ID>`


<b>The results are provided in JSON format as following:</b>

    If equal return that
    If not of equal size just return that
    If of same size provide insight in where the diffs are, actual diffs are not needed.
        § So mainly offsets + length in the data

### Technologies

This project was developed with:

    Java Development Kit (JDK) 1.8.0_221
    JUnit 5
    Apache Maven
    Eclipse 2019-06
    Spring Boot 2.1.4  
    
### Compile and Package

The API also was developed to run with an jar. In order to generate this jar, you should run:

    mvn package

It will clean, compile and generate a jar at target directory, e.g. `diff-api-1.0.0-jar-with-dependencies.jar`

### Test

It provides some methods to test:

• Unit and Integration Tests

For both, you can run:

    mvn test

Just for Unit Tests:

    mvn -Dtest=DiffApiApplicationUnitTests test

Just for Integration Tests, run:

    mvn -Dtest=DiffApiApplicationIntegrationTests test
    
### Run

In order to run the web service, run the uber jar simply as following:

    java -jar diff-api-1.0.0-jar-with-dependencies.jar

By default, the service will be available at 

    http://localhost:8080/