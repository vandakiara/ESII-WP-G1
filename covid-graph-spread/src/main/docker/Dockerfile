FROM openjdk:8-jre-alpine

WORKDIR ${application.workdir}
COPY ${application.dependencies} ${application.dependencies}
COPY ${project.build.finalName}.jar ${project.build.finalName}.jar
COPY assets/CovidGraphSpreadTemplate.html assets/CovidGraphSpreadTemplate.html

ENTRYPOINT ["/usr/bin/java", "-jar", "${project.build.finalName}.jar"]
EXPOSE 3001