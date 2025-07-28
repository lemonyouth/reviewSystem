FROM openjdk:17
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
CMD ["java", "-jar", "target/review-system-0.0.1-SNAPSHOT.jar"]
