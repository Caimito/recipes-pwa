FROM openjdk

LABEL org.opencontainers.image.authors="info@caimito.net"

ARG ARTIFACT_VERSION

RUN mkdir -p /opt/data
COPY target/recipe-api-$ARTIFACT_VERSION.jar /opt/app.jar

EXPOSE 8080
VOLUME /opt/data

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/opt/app.jar"]
