FROM openjdk:21-jdk-slim

RUN useradd -m -s /bin/bash appuser
USER appuser

WORKDIR /app

COPY build/libs/Cobooking-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8500

CMD ["java", "-jar", "app.jar"]


