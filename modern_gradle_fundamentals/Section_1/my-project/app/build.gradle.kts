plugins {
    id("my-application")
}

// Enable Gradle to run the application - Gradle Task
application {
    mainClass.set("com.example.MyApplication")
}

dependencies {
    implementation(platform("com.example:platform"))
    implementation(project(":data-model"))
    implementation(project(":business-logic"))

    runtimeOnly(libs.slf4j.simple)
}