FROM openjdk:8-jre-alpine

WORKDIR application
COPY lib lib
COPY covid-graph-spread-0.0.1-SNAPSHOT.jar covid-graph-spread-0.0.1-SNAPSHOT.jar
COPY assets/CovidGraphSpreadTemplate.html assets/CovidGraphSpreadTemplate.html

ENTRYPOINT ["/usr/bin/java", "-jar", "covid-graph-spread-0.0.1-SNAPSHOT.jar"]
EXPOSE 3001