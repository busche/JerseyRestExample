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
	curl -H "Accept: text/html" -X GET -i http://localhost:8080/myJersey/customers/all
	curl -H "Accept: text/plain" -X GET -i http://localhost:8080/myJersey/customers/all

and watch for different outputs.

