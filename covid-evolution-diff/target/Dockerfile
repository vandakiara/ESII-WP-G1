FROM openjdk:8-jre-alpine

WORKDIR application
COPY lib lib
COPY covid-evolution-diff-0.0.1-SNAPSHOT.jar covid-evolution-diff-0.0.1-SNAPSHOT.jar
COPY diff-template.html diff-template.html

ENTRYPOINT ["/usr/bin/java", "-jar", "covid-evolution-diff-0.0.1-SNAPSHOT.jar"]
EXPOSE 3000