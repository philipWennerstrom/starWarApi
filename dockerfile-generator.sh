#!/bin/sh

app_name=$1
app_version=$2
app_env=$3

printf \
'%s\n' \
'FROM openjdk:8' \
'ARG DEPENDENCY=target/dependency' \
'COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib' \
'COPY ${DEPENDENCY}/META-INF /app/META-INF' \
'COPY ${DEPENDENCY}/BOOT-INF/classes /app' \
'VOLUME /tmp' \
'VOLUME /logs' \
'EXPOSE 8080' \
'ENTRYPOINT ["java", "-server", "-Xms512m", "-Xmx4g", "-XX:+UseG1GC", "-Duser.timezone=America/Sao_Paulo", "-DapplicationName='"${app_name}"'", "-Dversion='"${app_version}"'", "-Denv='"${app_env}"'", "-Dbrand=b2sky", "-Dlog.level=INFO", "-Dlog.toStdout=true", "-Dlog.stdout.level=INFO", "-Dlog.format=json", "-Dlog.folder=./logs", "-Dlog.fileName='"${app_name}"'.log", "-Dlog.type=async", "-Dlog.appender=file", "-Dlog.async.maxFlushTime=180000", "-cp", "app:app/lib/*", "br.com.b2sky.infra.StarWarApplication"]' \
>Dockerfile
