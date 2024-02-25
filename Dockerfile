FROM --platform=linux/amd64 maven:3.9.6-amazoncorretto-17-al2023 as build

WORKDIR /elite-tickets-app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

FROM --platform=linux/amd64 eclipse-temurin:21-alpine

COPY --from=build /elite-tickets-app/target/EliteTickets-1.0-SNAPSHOT.jar .

CMD ["java", "-jar", "EliteTickets-1.0-SNAPSHOT.jar"]
