CMPE 275 Term Project

Survey Ape

URL: http://ec2-52-36-9-6.us-west-2.compute.amazonaws.com:8080/

Build Instruction:
1) Clone this Repository or download the zip file
2) Extract the zip
3) Open a terminal
4) run this command 
//skip tests
-> mvn clean install -Dmaven.test.skip=true
-> nohup java -jar surveyapp-0.0.1-SNAPSHOT.jar &
-> Open localhost:8080

Database-
1) Create MySQL Database- devopsbuddy with root user before running the program

