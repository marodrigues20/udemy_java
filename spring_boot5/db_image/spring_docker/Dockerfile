FROM postgres:alpine 

WORKDIR /usr/app

COPY schema.sql /.
COPY dmls.sql /.
COPY init.sh /docker-entrypoint-initdb.d/

