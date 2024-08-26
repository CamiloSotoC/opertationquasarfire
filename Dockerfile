FROM openjdk:17-slim

WORKDIR /app

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/opertationquasarfire.jar

EXPOSE 3000

CMD [ "java", "-jar", "/app/opertationquasarfire.jar" ]