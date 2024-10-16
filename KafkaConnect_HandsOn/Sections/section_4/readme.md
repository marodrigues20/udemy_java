# Section 4: Setup and Launch Kafka Connect Cluster

## 17. Important note for Docker Toolbox users

Important note for Docker Toolbox users
If you have installed Docker Toolbox (older Mac users and older Windows users), please note the following:

your IP will be 192.168.99.100, as you have seen in the video already. 
The docker machine that ships with Docker Toolbox comes with only 2GB of RAM, which is not enough for Kafka Connect Cluster. You need to increase it to at least 4GB by running the following commands:
docker-machine stop
VBoxManage modifyvm default --cpus 2
VBoxManage modifyvm default --memory 4096
docker-machine start


More information here: http://stackoverflow.com/questions/32834082/how-to-increase-docker-machine-memory-mac

If you're on Windows, please have a read here: https://www.ibm.com/developerworks/community/blogs/jfp/entry/Using_Docker_Machine_On_Windows?lang=en  As mentioned in the link, you can do:

docker-machine rm default
docker-machine create -d virtualbox --virtualbox-memory=4096 --virtualbox-cpu-count=2 default


## 18. Starting Kafka Connect Cluster using Docker Compose

