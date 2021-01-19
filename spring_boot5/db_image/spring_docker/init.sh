#!/bin/sh
set -e

psql -v ON_ERROR_STOP=1 --dbname template1 --username postgres <<-EOSQL
    CREATE USER springstudent;
    CREATE DATABASE "springcourse";
    GRANT ALL PRIVILEGES ON DATABASE springcourse TO springstudent;
EOSQL

# Configure the springcourse database as the springstudent user.
psql -U springstudent springcourse <<-EOSQL
    CREATE SCHEMA "springcourse";
    SET search_path TO springcourse;
    \i /usr/app/db-employee_directory/employee_directory_public_employee.sql
    \i /usr/app/db-hb_student_tracker/hb_student_tracker_public_student.sql
    \i /usr/app/db-hb-01-one-to-one-uni/hb_01_one_to_one_uni_public_instructor_detail.sql
    \i /usr/app/db-hb-01-one-to-one-uni/hb_01_one_to_one_uni_public_instructor.sql
    \i /usr/app/db-hb-03-one-to-many/hb_03_one_to_many_public_course.sql
    \i /usr/app/db-hb-04-one-to-many-uni/hb_04_one_to_many_uni_public_review.sql
    \i /usr/app/db-hb-05-many-to-many/hb_05_many_to_many_public_course_student.sql
    \i /usr/app/db-spring-security_demo_bcrypt/spring_security_demo_bcrypt_public_users.sql
    \i /usr/app/db-spring-security_demo_bcrypt/spring_security_demo_bcrypt_public_authorities.sql
    \i /usr/app/db-web_customer_tracker/web_customer_tracker_public_customer.sql
EOSQL
