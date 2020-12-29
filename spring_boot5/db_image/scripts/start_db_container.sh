#!/bin/sh

cd ../spring_docker
docker build -t marodrigues20/springcourse_udemy:latest .

docker run --name springcourse_udemy -d -e POSTGRES_PASSWORD=springstudent \
-p 5432:5432 marodrigues20/springcourse_udemy
sleep 15
