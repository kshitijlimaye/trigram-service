# Story content generation using trigram analysis

The trigram service has been created to allow user generate a new story based on supplied text content.
This service supports all popular languages and scripts.

## Prerequisites to  run
1. JDK 1.8
2. Maven 3.5 and above

## How to build the code
The project uses Maven as a build tool. 
To build the code, please run following command from directory where pom.xml file is
>mvn clean install

This should generate an executable JAR file inside /target folder.

## How to run the service
Copy the contents in the following file
>input.txt

And keep input.txt in same directory as that of the JAR file.

To start the program execution, run following command 
>java -jar trigram-service-0.0.1.jar

The generated story will be written in same directory in file 
>output.txt
