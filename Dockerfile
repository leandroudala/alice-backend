FROM eclipse-temurin:21-jdk-ubi9-minimal

# install required packages

RUN microdnf update -y && \
    microdnf install -y git vim && \
    microdnf clean all


# create non root user
ARG APP_USER=vscode
ARG APP_UID=1000
ARG APP_GID=1000

RUN groupadd --system --gid ${APP_GID} ${APP_USER}

RUN useradd --system --uid ${APP_UID} --gid ${APP_GID} -m -s /bin/bash ${APP_USER}

WORKDIR /app

USER ${APP_USER}
