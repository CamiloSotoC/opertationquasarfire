FROM openjdk:17

RUN mvn clean package

WORKDIR /app

EXPOSE 3000

CMD [ "java", "-jar", "target/opertationquasarfire-0.0.1-SNAPSHOT.jar" ]