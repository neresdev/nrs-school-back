# Use uma imagem base do MySQL
FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=RootPass*

ENV MYSQL_USER=develop
ENV MYSQL_PASSWORD=DevelopPass

ENV MYSQL_DATABASE=NRS_SCHOOL

COPY ./docker/setup.sql /docker/docker-entrypoint-initdb.d/
