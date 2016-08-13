# imixs-jsf-example


The Imixs-jsf-example provides a simple web application using the imixs workflow engine.
You can take this application as a scaffolding for your own web business application based on the Imixs-Workflow.

see:
[www.imixs.org](http://www.imixs.org)

# Build and Deploy

The Imixs-jsf-example  is based on Maven to build the project from sources run

    mvn clean install

To deploy the application you application sever need to provide a database pool named 'jdbc/workflow-db' and a JAAS security configuration named 'imixsrealm'
The security concept of imixs-workflow defines default roles:

* org.imixs.ACCESSLEVEL.NOACCESS
* org.imixs.ACCESSLEVEL.READACCESS
* org.imixs.ACCESSLEVEL.AUTHORACCESS
* org.imixs.ACCESSLEVEL.EDITORACCESS
* org.imixs.ACCESSLEVEL.MANAGERACCESS

Each user accessing the Imixs-Workflow engine should be mapped to one of these roles. The user roles can be mapped by configuration from the application server. You will find more information about the general ACL concept of the [Imixs-Workflow project site](http://www.imixs.org).

__NOTE:__ The Imixs-jsf-example is build on default for JBoss/Wildfly. You can use one of the GlassFish profiles to build for GlassFish Application server. See details below.

# Run the Application
After deployment you can start the sample application from:

[http://localhost:8080/workflow/](http://localhost:8080/workflow/)



## Upload BPMN Model using curl:

After you have successful deployed your application you can upload the Ticket Worklfow Model via the Rest Service API. Use the following curl command:

    curl --user admin:adminpassword --request POST -Tticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

The BPMN Model is part of the project and located under /src/workflow/ticket.bpmn


## Glassfish Support

The project uses the jax-rs implementation RestEasy which is part of JBoss/Wildfly Application server. To run the example on GlassFish you need to use the Jersey implementation. The project provides different maven profiles containing the necessary configuration details. To deploy the example on GlassFish4 or Payara run the maven install command with the profile 'glassfish4'

    mvn clean install -Pglassfish4

The project contains also a profile for GlassFish3 (Java EE5)