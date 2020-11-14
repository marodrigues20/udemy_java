#!/bin/sh
set -e

psql -v ON_ERROR_STOP=1 --dbname template1 --username postgres <<-EOSQL
    CREATE DATABASE "springcourse"
    WITH OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United Kingdom.1252'
    LC_CTYPE = 'English_United Kingdom.1252'
    CONNECTION LIMIT = -1
    TEMPLATE template0;
EOSQL

# Configure the springcourse database as the postgres user.
psql -U postgres springcourse <<-EOSQL
    CREATE SCHEMA springcourse;
    SET search_path TO springcourse,public;
    \i /schema.sql
    \i /dmls.sql
EOSQL
