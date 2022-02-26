FROM gradle:6.8.3-jdk11-hotspot AS build
ADD tmpDeps/ /root/.m2/repository/org/example/
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build

FROM openjdk:11.0.4-jre-slim

RUN mkdir /app
COPY --from=build /home/gradle/src/build/distributions/MonitoringInfra.tar /app/
WORKDIR /app/
RUN tar -xvf MonitoringInfra.tar
EXPOSE 8080
WORKDIR /app/MonitoringInfra
CMD bin/MonitoringInfra
