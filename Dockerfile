# Multi-stage solicitado: Eclipse Temurin JDK 21 para compilar; JRE 21 para ejecutar.
FROM eclipse-temurin:21-jdk AS build
RUN apt-get update && apt-get install -y --no-install-recommends curl unzip ca-certificates \
    && rm -rf /var/lib/apt/lists/*
ARG GRADLE_VERSION=9.2.1
RUN curl -fsSL "https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip" -o /tmp/gradle.zip \
    && unzip -q /tmp/gradle.zip -d /opt \
    && rm /tmp/gradle.zip
ENV PATH="/opt/gradle-${GRADLE_VERSION}/bin:${PATH}"
WORKDIR /app
COPY . .
RUN gradle --no-daemon clean bootJar

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd --system springboot && useradd --system --gid springboot --no-create-home springboot
COPY --from=build --chown=springboot:springboot /app/build/libs/gestion-inventario-0.0.1-SNAPSHOT.jar /app/app.jar
USER springboot
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
