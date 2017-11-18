FROM imixs/wildfly:1.1.0

# Imixs-Microservice Version 1.0.2
MAINTAINER ralph.soika@imixs.com

# Install Imixs-Admin Client
RUN wget https://github.com/imixs/imixs-admin/releases/download/4.2.6/imixs-admin-4.2.6.war \
 && mv imixs-admin-4.2.6.war $WILDFLY_DEPLOYMENT  

# add configuration files
COPY ./src/docker/configuration/* ${WILDFLY_CONFIG}/

# Copy sample application
COPY ./target/imixs-jsf-example-*.war $WILDFLY_DEPLOYMENT  

