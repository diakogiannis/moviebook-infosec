FROM maven:3.8-amazoncorretto-17 AS build
COPY . /usr/app/
RUN mvn -f /usr/app/pom.xml clean package

FROM amazoncorretto:17
COPY --from=build /usr/app/target/moviebook-runnable.jar /usr/local/lib/moviebook-runnable.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/moviebook-runnable.jar"]