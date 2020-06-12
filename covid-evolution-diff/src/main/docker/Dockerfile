FROM openjdk:8-jre-alpine

WORKDIR ${application.workdir}
COPY ${application.dependencies} ${application.dependencies}
COPY ${project.build.finalName}.jar ${project.build.finalName}.jar
COPY assets/diff-template.html assets/diff-template.html

ENTRYPOINT ["/usr/bin/java", "-jar", "${project.build.finalName}.jar"]
EXPOSE 3000