# JerseyRestExample
Sample Application using Jersey and Grizzly


This is mostly example code taken from the following sources:

* http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/griz_jersey_intro/Grizzly-Jersey-Intro.html
* http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/basic_grizzly_jersey/jersey-grizzly-json-service.html


# Run the example

Call 

	mvn clean compile
	mvn exec:java

in order to launch the example from the command line.

Thereafter, try the following on another console:


	curl -H "Accept: application/json" -X GET -i http://localhost:8080/myJersey/customers/all
	curl -H "Accept: application/xml" -X GET -i http://localhost:8080/myJersey/customers/102
	curl -H "Accept: text/html" -X GET -i http://localhost:8080/myJersey/customers/all
	curl -H "Accept: text/plain" -X GET -i http://localhost:8080/myJersey/customers/all
	

and watch for different outputs.

Call update / delete on the REST-Target,
## on Windows

### update Customers

compare the output of the following examples: 

	curl -X PUT -i -H "Content-Type:application/json" -d "{\"id\":102,\"firstName\":\"Abigail\",\"lastName\":\"Adams\",\"email\":\"aadams@example.com\",\"city\":\"Braintree\",\"state\":\"MA\",\"birthday\":\"1744-11-22\"}" http://localhost:8080/myJersey/customers/102/update

	curl -X PUT -i -H "Content-Type:application/json" -d "{\"id\":102,\"firstName\":\"Abigail\",\"lastName\":\"Adams\",\"email\":\"aadams@example.com\",\"city\":\"Braintree\",\"state\":\"MA\",\"birthday\":\"1744-11-22\"}" http://localhost:8080/myJersey/customers/106/update

Note that this extends from the linked examples above. I added a check for matching customer IDs (106 vs. 102) s.t. the latter fails with an HTTP 400 error.

### Add Customers

Call the following to add Customers

	curl -X POST -i -H "Content-Type: application/json" -d "{\"firstName\":\"Laura\",\"lastName\":\"Stuart\",\"email\":\"lstuart@example.com\",\"city\":\"Braintree\",\"state\":\"MA\",\"birthday\":\"1744-11-22\"}" http://localhost:8080/myapp/customers/add

Internally the parameterless constructor, Customer(), is used. Why this works, is currently magic to me ... I assume this lies in the power of Jackson.



## on Linux

	curl -X PUT -i -H "Content-Type: application/json" -d '{"id":102, "firstName":"Abigail","lastName":"Adams","email":"aadams@example.com","city":"Braintree","state":"MA","birthday":"1744-11-22"}' http://localhost:8080/myJersey/customers/102/update
 