# Section 4: Instalation

## 9. Download Java

- Oracle (license on production)
- OpenJDK, Amazon Coretto (free on production)
- This course uses Amazon Coretto
- Minimum Java 11
- This course uses Java 17

## 10. Install Java

- Instalation description

## 11. Kafka Installation This Course

- Binaries / native installation
- Docker (This course use it.)
- My machine: Windows 11 professional Edition
- Suitable for development, not for production

## 12. Docker Introduction

- Not a docker course
- For easy installation
- Image: contains everything to run the application
- Container: instance of image, run an application

## 13. Install Kafka Docker

### Docker Compose

- More than container to run application
- Example
- This course uses several containers
- Script to run several containers at once
- Using docker compose

### Kafka on Docker

- To be "installed"
  - Zookeeper
  - Kafka
- Docker compose script available on lecture Resources & References (last section)
  - Once or more docker containers
  - Configure & run using docker-compose yml script

- At the last section of the course, on lecture titled resources & references, please download docker scripts.
- There are several docker compose scripts there, for different purpose of course parts.
- Put them into some directory.
- In its simplest form, running docker compose script will only requires this statement.
- Running docker compose script download docker images, create docker containers required, and then configure and start them.
- But in this course, we will use several docker compose script, different for each part, where the complexity will increases along with the course.

To run docker compose just type:

$ docker-compose up

However, how we have many docker compose files we are going to use the bellow command to select the required docker compose file:

$ docker-compose -f [script-file] -p [project] up -d

```
| File, to replace [script-file] | Replace [project] |                  Complete command                                             |
| docker-compose-core.yml        | core              | $ docker-compose -f docker-compose-core.yml -p core up -d                     |
| docker-compose-connect.yml     | connect           | $ docker-compose -f docker-compose-connect.yml -p connect up -d               |
| docker-compose-connect-sample  | connect-sample    | $ docker-compose -f docker-compose-connect-sample.yml -p connect-sample up -d |
| docker-compose-full.yml        | full              | $ docker-compose -f docker-compose-full.yml -p full up -d                     |
| docker-compose-full-sample.yml | full-sample       | $ docker-compose -f docker-compose-full-sample.yml -p full-sample up -d       |
```

- To remove the container
$ docker-compose -f [script-file] -p [project] down

- What to run in sequence

```
| Part             | What to run (In sequence) |
| 1-core kafka     | $ docker-compose -f docker-compose-core.yml -p core up -d                     |
| 2-kafka connect  | $ docker-compose -f docker-compose-core.yml -p core down                      |
                   | $ docker-compose -f docker-compose-connect.yml -p connect up -d               |
                   | $ docker-compose -f docker-compose-connect-sample.yml -p connect-sample up -d |  
| 3-Kafka full     | $ docker-compose -f docker-compose-connect.yml -p connect down                |
                   | $ docker-compose -f docker-compose-connect-sample.yml -p connect-sample down  |
                   | $ docker-compose -f docker-compose-full.yml -p full up -d                     |
                   | $ docker-compose -f docker-compose-full-sample.yml -p full-sample up -d       |                    


## 14. Download Eclipse

- Normal Installation Description not required.

## 15. Install Eclipse

- Description not required



