FROM postgres:alpine

WORKDIR /usr/app

COPY . .

COPY init.sh /docker-entrypoint-initdb.d/
