FROM imixs/wildfly

# Imixs-Microservice Version 1.0.2
MAINTAINER ralph.soika@imixs.com

# add configuration files
COPY ./src/docker/configuration/* ${WILDFLY_CONFIG}/

# Copy sample application
COPY ./target/imixs-jsf-example-*.war $WILDFLY_DEPLOYMENT  

