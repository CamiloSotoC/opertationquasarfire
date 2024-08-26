FROM openjdk:17

RUN mvn install

WORKDIR /app

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/opertationquasarfire-0.0.1-SNAPSHOT.jar

EXPOSE 3000

CMD [ "java", "-jar", "/app/opertationquasarfire-0.0.1-SNAPSHOT.jar" ]