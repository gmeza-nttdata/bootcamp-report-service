FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN wget https://raw.githubusercontent.com/gmeza-nttdata/bootcamp-config-data/docker/other/bootstrap.yml
ENTRYPOINT ["java","-jar","/app.jar"]
