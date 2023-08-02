ARG BASE=gradle:7.3.1-jdk11-alpine
# gradle:8.1-jdk11-alpine is problematic
FROM $BASE 
WORKDIR /work
COPY build.gradle gradle.properties ./
ADD gradle ./gradle/
ADD src ./src/
CMD ["gradle", "test"]
