plugins {
	java
	id("org.springframework.boot") version "2.7.11"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.course.kafka"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val apacheCommonsLang3Version = "3.12.0"
val jacksonVersion = "2.13.2"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.commons:commons-lang3:${apacheCommonsLang3Version}")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${jacksonVersion}")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
