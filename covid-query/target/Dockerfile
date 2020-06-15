FROM openjdk:8-jre-alpine

WORKDIR application
COPY lib lib
COPY covid-query-0.0.1-SNAPSHOT.jar covid-query-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "covid-query-0.0.1-SNAPSHOT.jar"]
# COLOCAR O PORTO DO SEU PROJETO
EXPOSE 3003