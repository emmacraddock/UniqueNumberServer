## Unique Number Logger ##

This application records a log of de-duplicated unique numbers
--
###### Requirements ######
This project runs with the following:
* Java 11
* Java SDK 11
* Gradle 5.2

---
#### Assumptions ####
This project makes a the following assumptions:
1. input will be provided to `127.0.0.1 4000`
2. a server-native newline character is represented as `\n`
3. the application will close when the following input is received from any client: `terminate` and followed by a server-native newline  
3.1 this is case-sensitive
4. given a `terminate` input, all in-flight processing will complete and the application will close without errors
5. given any input that is not `terminate` or a 9-digit numerical string, the client will close immediately, and the application will remain running
6. only unique numbers will be written to numbers.log
7. numbers.log is cleared and recreated every time the application runs and will be located at the root of this project
###### Build ######
To build run the following command from the root of this project:
`./gradlew build`

###### Run ######
To start up the Application, run `./gradlew run` from the root directory to start up the server.
* You can connect to the server here: `127.0.0.1` on `port 4000` - netcat provides a simple way to connect from a terminal: `nc 127.0.0.1 4000`

###### Tests ######
Run all unit tests with the following command: `./gradlew test`
This will return either a successful build if all tests pass, a failed build if any tests fail.
