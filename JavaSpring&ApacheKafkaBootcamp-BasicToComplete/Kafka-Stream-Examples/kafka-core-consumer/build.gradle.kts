plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "com.course.kafka"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val jacksonVersion = "2.13.2"
val caffeineVersion = "3.0.6"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}") //This is being added to work with JSON
	implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}") //This is being added to work with JSON
	implementation("com.github.ben-manes.caffeine:caffeine:${caffeineVersion}") //Section 07: 39. Idempotency
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
