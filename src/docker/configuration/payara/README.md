# Payara

This folder contains configuration files used to build a docker container running payara.

To build the Docker Image for Payra run:

	$ mvn clean install -Pdocker-build-payara
	
After you have build the payara image with the sample application you can start the corresponding Docker-Stack with:

	$ docker-compose -f docker-compose-payara.yml up