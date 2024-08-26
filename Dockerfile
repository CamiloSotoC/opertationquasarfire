FROM maven:eclipse-temurin:17-jdk

CMD [ "maven", "install" ]

WORKDIR /app

COPY target/opertationquasarfire-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 3000


CMD [ "java", "-jar", "/app/app.jar" ]