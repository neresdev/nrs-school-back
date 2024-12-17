FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=RootPass*

ENV MYSQL_DATABASE=NRS_SCHOOL
ENV MYSQL_USER=develop
ENV MYSQL_PASSWORD=DevelopPass

COPY ./setup.sql /docker-entrypoint-initdb.d/

EXPOSE 3306