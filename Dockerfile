FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# copy pom and wrapper first to leverage Docker layer caching
COPY pom.xml mvnw ./
COPY .mvn .mvn
COPY src ./src
COPY proto ./proto

RUN mvn -B -DskipTests package

FROM eclipse-temurin:21-jre
WORKDIR /app

# run as non-root for production hardening
RUN useradd -r -u 10001 -g root -s /sbin/nologin app

# copy the built jar from the build stage
COPY --from=build /app/target/*.jar /app/app.jar
RUN chown -R app:root /app

ENV JAVA_TOOL_OPTIONS="-XX:InitialRAMPercentage=25 -XX:MaxRAMPercentage=75 -Djava.security.egd=file:/dev/./urandom -Dspring.output.ansi.enabled=NEVER"

EXPOSE 9093

USER app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
