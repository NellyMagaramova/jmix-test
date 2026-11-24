FROM gradle:8.14-jdk21
USER root
WORKDIR /app
RUN useradd --system --uid 10001 app
COPY build/libs/jmix-test-0.0.3-SNAPSHOT.jar /app/app.jar
USER 10001
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=70", "-jar", "/app/app.jar"]
