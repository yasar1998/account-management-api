FROM openjdk:9
MAINTAINER yashar
COPY target/account-management-api-0.0.1-SNAPSHOT.jar account-management-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/account-management-api-0.0.1-SNAPSHOT.jar"]