FROM openjdk:11 AS build
COPY . .
RUN ./mvnw clean install


FROM openjdk:11 AS deploy
WORKDIR /app

COPY --from=build /target/weather-1.0.0.jar /app/weather.jar

ENTRYPOINT [ "java", "-jar", "weather.jar"]
