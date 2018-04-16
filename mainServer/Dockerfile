# Build the jar
FROM maven:3.5-jdk-8-alpine as build 
WORKDIR /app
COPY . /app
RUN mvn install

# Start the app
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=build /app/target/dataimporter-jar-with-dependencies.jar .
EXPOSE 8080
CMD ["java","-jar", "dataimporter-jar-with-dependencies.jar"]