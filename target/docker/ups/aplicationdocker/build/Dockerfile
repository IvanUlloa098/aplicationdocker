FROM jboss/wildfly
ENV WILDFLY_USER user
ENV WILDFLY_PASS userPassword

ENV JBOSS_CLI $JBOSS_HOME/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR $JBOSS_HOME/standalone/deployments/

RUN echo "Adding WildFly administrator"
RUN $JBOSS_HOME/bin/add-user.sh -u $WILDFLY_USER -p $WILDFLY_PASS --silent

COPY maven/aplicationdocker.war ${JBOSS_HOME}/standalone/deployments/
EXPOSE 8080
EXPOSE 8009
EXPOSE 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
