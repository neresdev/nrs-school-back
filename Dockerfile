# Use uma imagem base do MySQL
FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=sua_senha

ENV MYSQL_USER=usuario
ENV MYSQL_PASSWORD=senha

ENV MYSQL_DATABASE=escola

COPY ./docker/setup.sql /docker/docker-entrypoint-initdb.d/