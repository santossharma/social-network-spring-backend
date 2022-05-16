# FROM --platform=linux/x86_64 alpine:3.13.5
#FROM adoptopenjdk/openjdk11:alpine-jre

#FROM eclipse-temurin:11.0.13_8-jre-focal

FROM bellsoft/liberica-openjdk-alpine-musl:11
ADD target/social-network-spring-backend-1.1.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

