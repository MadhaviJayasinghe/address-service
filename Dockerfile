FROM openjdk:11-jre-slim-buster
ADD target/address-service-0.0.1-SNAPSHOT.jar address-service-0.0.1-SNAPSHOT.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "address-service-0.0.1-SNAPSHOT.jar"]