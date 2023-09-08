FROM openjdk:17-jdk
WORKDIR /app
COPY build/libs/auction-price-updater-0.0.1-SNAPSHOT.jar /app/auction-price-updater.jar
CMD ["java", "-jar", "/app/auction-price-updater.jar"]