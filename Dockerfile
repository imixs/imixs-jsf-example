FROM imixs/wildfly:1.2.9

# Imixs-Microservice Version 1.0.2
MAINTAINER ralph.soika@imixs.com

# add configuration files
COPY ./src/docker/configuration/wildfly/*.properties ${WILDFLY_CONFIG}/
COPY ./src/docker/configuration/wildfly/standalone.xml ${WILDFLY_CONFIG}/

# Copy sample application
COPY ./target/imixs-jsf-example.war $WILDFLY_DEPLOYMENT  

