Marketplace API:
Marketplace for Self-Employed. The marketplace allows employers to post jobs, while perspective self-employed can bid for projects. 

Getting Started
	Prerequisites:
		1) Java 1.7 
		2) Maven 
		3) Tomcat
		4) Eclipse or any IDE to view the code
		5) The project uses embedded Database. No external Database required

Building project:
	All the dependencies are added to marketplace\pom.xml
	1) Navigate to the project folder and Run below command to build the project
	mvn clean install
	2) To launch the project in eclipse, run below command to generate eclipse files. And then open the project in eclipse
	mvn eclipse:eclipse
	
Deploying:
	Deploy generated war file to tomcat through maven or eclipse. 
	
Refer the document marketplace_api_designdocument.doc for different api endpoints, sample request and response objects.


	
