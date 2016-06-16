# imixs-jsf-example


The Imixs-jsf-example provides a simple web application using the imixs workflow engine.
You can take this application as a scaffolding for your own web business application based on the Imixs-Workflow.

see:
http://www.imixs.org

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

## Wildfly Support


The project uses the jax-rs implementation Jersey which is part of GlassFish Application server. To run the example on JBoss/Wildfly you need to use the RestEasy implementation. A maven profile contains the necessary configuration details. To deploy the example on wildfly run

    mvn clean install -Pwildfly



#Upload BPMN Model using curl:

After you have successfull deployed your application you can upload a Imixs BPMN model via the Rest Service API. Use the following curl command:

    curl --user admin:adminpassword --request POST -Tticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

