FROM maven:3.9.9-jdk-17

RUN mvn install

WORKDIR /app

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 3000

CMD [ "java", "-jar", "/app/app.jar" ]