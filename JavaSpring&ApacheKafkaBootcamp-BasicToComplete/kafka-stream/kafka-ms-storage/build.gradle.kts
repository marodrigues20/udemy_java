plugins {
	java
	id("org.springframework.boot") version "2.7.11-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.course.kafka"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

val caffeineVersion = "3.0.6"
val jedisVersion = "4.2.1"
val jacksonVersion = "2.13.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.kafka:spring-kafka")

	implementation("com.github.ben-manes.caffeine:caffeine:${caffeineVersion}")
	implementation("redis.clients:jedis:${jedisVersion}")
	implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")


	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
