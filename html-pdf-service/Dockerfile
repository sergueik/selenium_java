FROM openjdk:8-jre-alpine3.9
ARG app_jar="html-pdf-service.war"
ADD "target/${app_jar}" app.war
VOLUME /logs
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.war"]
