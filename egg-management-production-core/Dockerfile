FROM openjdk:8-jdk-alpine
MAINTAINER malvern dongeni<malverndongeni@gmail.com>
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} data/apps/services/egg-management-production-core.jar
ENTRYPOINT ["java","-jar","data/apps/services/egg-management-production-core.jar"]