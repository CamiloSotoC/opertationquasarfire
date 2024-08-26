FROM openjdk:17

RUN mvn clean package

WORKDIR /app

EXPOSE 3000

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/opertationquasarfire-0.0.1-SNAPSHOT.jar

CMD [ "java", "-jar", "/app/opertationquasarfire-0.0.1-SNAPSHOT.jar" ]