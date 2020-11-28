FROM jboss/wildfly
ENV MYSQL_VERSION 5.1.49
ENV JBOSS_CLI /opt/jboss/wildfly/bin/jboss-cli.sh
ENV DEPLOYMENT_DIR /opt/jboss/wildfly/standalone/deployments/

COPY build/libs/LibraryServer.jar /opt/jboss/wildfly/standalone/deployments/
RUN /opt/jboss/wildfly/bin/add-user.sh admin h8nNgw5v/rHDmgON --silent


#RUN echo "=> Starting WildFly server" && \
#      bash -c '$JBOSS_HOME/bin/standalone.sh &' && \
#    echo  "=> Waiting for the server to boot" && \
#      bash -c 'until `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null | grep -q running`; do echo `$JBOSS_CLI -c ":read-attribute(name=server-state)" 2> /dev/null`; sleep 1; done' && \
#    echo "=> Downloading MySQL driver" && \
#      curl --location --output /tmp/mysql-connector-java-${MYSQL_VERSION}.jar --url http://search.maven.org/remotecontent?filepath=mysql/mysql-connector-java/${MYSQL_VERSION}/mysql-connector-java-${MYSQL_VERSION}.jar && \
#    echo "=> Adding MySQL module" && \
#      $JBOSS_CLI --connect --command="module add --name=com.mysql.driver --resources=/tmp/mysql-connector-java-${MYSQL_VERSION}.jar --dependencies=javax.api,javax.transaction.api" && \
#    echo "=> Adding MySQL driver" && \
#                                     #/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql,driver-xa-datasource-class-name=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource)"
#      $JBOSS_CLI --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)"


#RUN echo "=> Shutting down WildFly and Cleaning up" && \
#      $JBOSS_CLI --connect --command=":shutdown" && \
#      rm -rf $JBOSS_HOME/standalone/configuration/standalone_xml_history/ $JBOSS_HOME/standalone/log/* && \
#      rm -f /tmp/*.jar

# Expose http and admin ports
EXPOSE 8080 9990

#ENTRYPOINT /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement=0.0.0.0
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]