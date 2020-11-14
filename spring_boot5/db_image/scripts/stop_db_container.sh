#!/bin/sh

containerName=$(docker ps --format '{{.Names}}')

if [[ $containerName == "springcourse_udemy"  ]]; then
    echo Container "$containerName" is running.
    docker stop $(docker ps --format '{{.Names}}')
    echo Container "$containerName" stopped.
    docker rm springcourse_udemy
else
	echo Container springcourse_udemy is not running.
	docker rm springcourse_udemy
fi
