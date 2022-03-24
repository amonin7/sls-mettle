FROM gradle:jdk17
ARG JAR_FILE=build/libs/mettle-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]

