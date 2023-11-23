CREATE USER 'develop'@'%' IDENTIFIED BY 'DevelopPass';

GRANT ALL PRIVILEGES ON NRS_SCHOOL.* TO 'develop'@'%';

USE nrs_school;

create table students (
    id int AUTO_INCREMENT primary key,
    name varchar(100)
);
