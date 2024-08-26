FROM openjdk:17

RUN mvn install

EXPOSE 3000

CMD [ "java", "-jar", "target/opertationquasarfire-0.0.1-SNAPSHOT.jar" ]