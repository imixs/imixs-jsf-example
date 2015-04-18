imixs-jsf-example
=================

The Imixs-jsf-example provides a simple web application using the imixs workflow engine.
You can take this application as a scaffolding for your own web business application based on the Imixs-Workflow.

see:
http://www.imixs.org

Upload BPMN Model using curl:
=================================

You can upload a Imixs BPMN model using the curl command:

curl --user admin:adminpassword --request POST -Tticket.bpmn http://localhost:8080/workflow/rest-service/model/bpmn

