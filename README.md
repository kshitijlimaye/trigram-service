# Trigram based story creation

The trigram service has been created to allow user generate a new story based on supplied textual content.
The character encoding used is UTF-8, which means trigram service supports all popular languages and scripts.

## Prerequisites to  run
1. JDK 1.8
2. Maven 3.5 and above

## How to build the code
The project uses Maven as a build tool. To build the code, please run following command
>mvn clean install

This should generate an executable JAR file inside target folder.

## How to run the service
Copy the contents in the following file
>input.txt

And keep input.txt in same directory as that of the JAR file.

To start the program execution, run following command 
>java -jar trigram-service-0.0.1.jar

The generated story will be written in same directory in file 
>output.txt
