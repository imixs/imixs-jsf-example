# imixs-jsf-example


The Imixs-jsf-example provides a simple web application using the imixs workflow engine.
You can take this application as a scaffolding for your own web business application based on the [Imixs-Workflow project](http://www.imixs.org).


## 1. Build the Application

The Imixs-jsf-example  is based on Maven to build the project from sources run

    mvn clean install
    
You can also download the application from the [latest release](https://github.com/imixs/imixs-jsf-example/releases).    

## 2. Deploy the Application
To deploy the application successfully, the application sever need to provide a valid database pool named 'jdbc/workflow-db' and a JAAS security configuration named 'imixsrealm'. You will find an installation guide [here](http://www.imixs.org/doc/sampleapplication.html).

### Setup Security Roles
The security concept of imixs-workflow defines default roles:

* org.imixs.ACCESSLEVEL.NOACCESS
* org.imixs.ACCESSLEVEL.READACCESS
* org.imixs.ACCESSLEVEL.AUTHORACCESS
* org.imixs.ACCESSLEVEL.EDITORACCESS
* org.imixs.ACCESSLEVEL.MANAGERACCESS

Each user accessing the Imixs-Workflow engine should be mapped to one of these roles. The user roles can be mapped by configuration from the application server. You will find more information about the general ACL concept of the [Imixs-Workflow Deployent guide](http://www.imixs.org/doc/deployment/security.html).

__NOTE:__ The Imixs-jsf-example is tested with JBoss/Wildfly and GlassFish4/Payara Servers.

## 3. Run the Application
After deployment you can start the sample application from:

[http://localhost:8080/workflow/](http://localhost:8080/workflow/)

<br><br><img width="75%" src="sample.png">


# The Imixs Rest-API

Imixs-Workflow provides a powerfull Rest API. Also the JSF-Sample Application has included this API which is based on the jax-rs specification. 
To access the rest api in this sample application use the root api URL:

    http://localhost:8080/workflow/rest-service/

Find details about the Imixs REST api [here](http://www.imixs.org/doc/restapi/index.html). 

## Upload the BPMN Model:

After you have successful deployed your application you can upload the Ticket workflow model via the [Imixs-REST Service API](http://www.imixs.org/doc/restapi/index.html). 

<br><br><img src="model-ticket.png">

Use the following curl command to upload the model from your workspace:

    curl --user admin:adminpassword --request POST -Tsrc/workflow/ticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

The BPMN Model is part of the project and located under /src/workflow/ticket.bpmn


## Reporting

Imixs-Workflow provides a reporting interface which can be accessed by the [Imixs Rest API](http://www.imixs.org/doc/restapi/reportservice.html). A report definition can be created with the Eclipse Plug-in "Imixs-Report" which is part of the [Imixs-BPMN project](http://www.imixs.org/doc/modelling/index.html).

A new report definition can be uploaded with the curl commandline tool:

    curl --user admin:adminpassword --request POST -H "Content-Type: application/xml" -Tsrc/workflow/tickets.imixs-report http://localhost:8080/workflow/rest-service/report






<br><br><img src="small_h-trans.png">


The Imixs-JSF-Example includes a Docker Container to run the sample application in a Docker container. 
The docker image is based on the docker image [imixs/wildfly](https://hub.docker.com/r/imixs/wildfly/).

To run Sample Application in a Docker container, the container need to be linked to a postgreSQL database container. The database connection is configured in the Wildfly standalone.xml file and can be customized to any other database system. 

## 1. Build the Application
Before you can start the container, build the application and the docker image from sources:


	mvn clean install -Pdocker-build
	
## 2. Starting the Application in a Docker Container

After you have build the application and the Docker image you can start the application. The workflow engine needs a SQL Database. Both containers can be started with one docker-compose command

	docker-compose up

See the docker-compose.yml file for details

The Docker container creates user accounts for testing with the following userid/password:

    admin=adminpassword
    manfred=password
    anna=password

After your application was started, upload the ticket.bpmn example model:

	curl --user admin:adminpassword --request POST -Tticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

and run the application in a web browser:	

	http://localhost:8080/workflow/
	
	
## Development

During development you can use the docker-compose-dev.yml file. This stack maps the src/docker/deployments folder to the wildfly auto deploy directory. 

	$ docker-compose -f docker-compose-dev.yml up
	
you may have to grant the deployment folder first to allow the docker non privileged user to access this location.

	$ sudo chmod 777 src/docker/deployments/
	
	
	 