# Section 5. Setup and Launch Kafka

## 30. Increase RAM for Docker - Please Read

Increasing RAM for Docker - PLEASE READ
Please follow the instructions based on the docker version you installed. 

==================================================================


- If you have installed Docker for Mac or Docker for Windows (not Toolbox), please do the following:

click the Docker icon in the bar, and select Preferences , 
then the Docker for Mac/Windows 's preference windows should show up, 
then click Advanced  tab, you will see the memory adjust bar.
Set the memory to at least 4.0 GB (6.0 GB if you can). 

==================================================================


- If you have installed Docker Toolbox (older Mac users and older Windows users), please note the following:

your IP will be 192.168.99.100 , as you have seen in the video already. 
The docker machine that ships with Docker Toolbox comes with only 2GB of RAM, which is not enough for Kafka. You need to increase it to 4GB (at least) by running the following commands:
docker-machine rm default
docker-machine create -d virtualbox --virtualbox-memory=4096 --virtualbox-cpu-count=2 default
3. Run the command 

docker-machine env default --shell cmd 

or 

docker-machine env default --shell powershell 

4. In a new command prompt, paste the output from the command above into the terminal

!!!! MAKE SURE TO COPY YOUR OUTPUT, NOT THE CODE BELOW !!!!
set DOCKER_TLS_VERIFY=1
set DOCKER_HOST=tcp://192.168.99.100:2376
set DOCKER_CERT_PATH=...
set DOCKER_MACHINE_NAME=default 
5. you can now run the course commands. If you open new terminal, make sure to repeat step 3 and 4. 

--------------------------

Docker Toolbox - Alternative to step 2 (only run if the above commands didn't work):

docker-machine stop
VBoxManage modifyvm default --cpus 2
VBoxManage modifyvm default --memory 4096
docker-machine start



## 31. Frequently Asked Question - Common Issues

Frequently Asked Questions - Common Issues
If you get errors in the next lecture, here is a list of the most common ones. Please refer to this lecture before asking anything in the Q&A. 

> Operating system RAM available is 1536 MiB, which is less than the lowest recommended of 5120 MiB. Your system performance may be seriously impacted.

You need to increase the Docker RAM to at least 4GB. Please see the lecture 20 (increasing RAM for Docker) for instructions. You may still have the warning, but the message will say RAM available is 3000 MiB (or so), which is enough for this course. 

> REST Proxy and Schema Registry keep on restarting

You may have set the wrong ADV_HOST   environment variable. Please set it to 127.0.0.1  if using Docker for Mac or Docker for Windows, or please set it to 192.168.99.100  if using Docker Toolbox. 

> Zookeeper keeps exiting

You may not have enough RAM on your Docker or machine. Please refer to lecture 20.

> I can't install Docker (for various reasons)

Unfortunately I can't debug these issues, please use Google to try to find similar problems other users may have experienced. Try installing Docker Toolbox as an alternative. 

> Coyote Health checks show 26% (or a low %)

Could be a proxy issue, a RAM issue, or something else. Please try to increase the RAM or ensure you're not behind a proxy. 

> Coyote Health checks show 97.6% 

You're fine to go ahead with the course. It's not 100% most likely due to a bug in Coyote, nothing to be worried about

> Error binding at port:Listen tcp 0.0.0.0:2181: bind : address already in use could not successfully bind to port 2181.please free the port and try again. (or any other port)

You have components already listening on the ports used for this course (2181, 9092, 3030, 8081, 8082, 8083). You are able to provide additional environment variables to change the ports. For example, if you wanted to change the zookeeper port from 2181 to 3181, you would add -e ZK_PORT=3181 -p 3181:3181  in your command. Please see https://github.com/Landoop/fast-data-dev#custom-ports for more information and other environment variables. 

> org.apache.kafka.common.errors.TimeoutException: Failed to update metadata after 60000 ms.

These are common when your Kafka port is wrong, or when the Kafka Advertised host (ADV_HOST ) is wrong. Please check the command you used to start Kafka, and check the advertised host (127.0.0.1  if using Docker for Mac or Docker for Windows, 192.168.99.100  if using Docker toolbox). 

> docker: Error response from daemon: Get https://registry-1.docker.io/v2/: net/http: request canceled while waiting for connection (Client.Timeout exceeded while awaiting headers).

It appears that some dns settings are blocked by Docker. So if you are running on a Mac go to System Preferences > Network > Advanced > DNS  Then hit the + button and add a new DNS Server use 8.8.8.8. save the settings and restart Docker and close and open a new terminal window. Type in 'docker run hello-world' and hopefully that fixes it.

Some countries/company might block the AWS/https access for some reasons. In such case, you can set a proxy/VPN in your machine, then try the download again, or using a docker registry mirror service, which is must have for using docker when I in China.

> Unable to find image 'landoop/fast-data-dev:latest' locally docker: Error response from daemon: Get https://registry-1.docker.io/v2/landoop/fast-data-dev/manifests/latest: unauthorized: incorrect username or password. 

Try running docker login 

> I don't have enough RAM on my laptop, can I run in AWS?

I don't recommend this, but please find instructions here: https://www.udemy.com/apache-kafka-series-kafka-from-beginner-to-intermediate/learn/v4/questions/2934476 

> I need to access Landoop from a remote Server

Please see these answers: https://www.udemy.com/apache-kafka-series-kafka-from-beginner-to-intermediate/learn/v4/questions/2312726 , https://www.udemy.com/apache-kafka-series-kafka-from-beginner-to-intermediate/learn/v4/questions/2950086 , https://www.udemy.com/apache-kafka-series-kafka-from-beginner-to-intermediate/learn/v4/questions/2929610


## 32. Starting Kafka using Docker Compose

### Kafka Setup

    - Please watch this lecture even if you did the Kafka for Beginners

    - We will see how to start a Kafka Cluster using Docker Compose
    - I will also show you how to get the Confluent binaries for the commands we run

    

NOTE 1: WE GONNA USE DOCKER-COMPOSE TO USE Schema Registry, Confluent REST Proxy
NOTE 2: The docker-compose file is located in code/2-start-kafka


