plugins {
    id("my-application")
}

// Enable Gradle to run the application
application {
    mainClass.set("com.example.MyApplication")
}

dependencies {
    implementation(project(":data-model"))
    implementation(project(":business-logic"))
}