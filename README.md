# imixs-jsf-example


The Imixs-jsf-example provides a simple web application using the imixs workflow engine.
You can take this application as a scaffolding for your own web business application based on the Imixs-Workflow.

see:
[www.imixs.org](http://www.imixs.org)

## 1. Build the Application

The Imixs-jsf-example  is based on Maven to build the project from sources run

    mvn clean install

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

# The Imixs Rest-API

Imixs-Workflow provides a powerfull Rest API. Also the JSF-Sample Application has included this API which is based on the jax-rs specification. 
To access the rest api in this sample application use the root api URL:

    http://localhost:8080/workflow/rest-service/

Find details about the Imixs REST api [here](http://www.imixs.org/doc/restapi/index.html). 

## Upload BPMN Model using curl:

After you have successful deployed your application you can upload the Ticket Worklfow Model via the Rest Service API. Use the following curl command:

    curl --user admin:adminpassword --request POST -Tticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

The BPMN Model is part of the project and located under /src/workflow/ticket.bpmn


## Reporting

Imixs-Workflow provides a reporting interface which can be accessed by the [Imixs Rest API](http://www.imixs.org/doc/restapi/reportservice.html). A report definition can be created with the Eclipse Plug-in "Imixs-Report" which is part of the [Imixs-BPMN project](http://www.imixs.org/doc/modelling/index.html).

A new report definition can be uploaded with the curl commandline tool:

    curl --user admin:adminpassword --request POST -H "Content-Type: application/xml" -Ttickets.imixs-report http://localhost:8080/workflow/rest-service/report



