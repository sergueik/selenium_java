FROM openjdk:8-jdk-alpine
VOLUME /logs
EXPOSE 8080
ARG JAR_FILE
ADD ${JAR_FILE} html-pdf-service.war
ENTRYPOINT ["java","-jar","/html-pdf-service.war"]