FROM openjdk:8-jre
EXPOSE 8080
WORKDIR /tmp
ADD /target/votacao*.jar /tmp/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
