FROM openjdk:17-slim

RUN mvn install

WORKDIR /app

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 3000

CMD [ "java", "-jar", "/app/app.jar" ]