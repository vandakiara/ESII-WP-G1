FROM openjdk:8-jre-alpine

WORKDIR ${application.workdir}
COPY ${application.dependencies} ${application.dependencies}
COPY ${project.build.finalName}.jar ${project.build.finalName}.jar
COPY assets/config.ini assets/config.ini

ENTRYPOINT ["/usr/bin/java", "-jar", "${project.build.finalName}.jar"]
# COLOCAR O PORTO DO SEU PROJETO
EXPOSE 3002