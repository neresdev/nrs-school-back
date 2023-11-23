FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=RootPass*

ENV MYSQL_DATABASE=nrs_school
ENV MYSQL_USER=develop
ENV MYSQL_PASSWORD=DevelopPass

COPY ./docker/setup.sql /docker-entrypoint-initdb.d/

EXPOSE 3306