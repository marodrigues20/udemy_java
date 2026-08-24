import com.example.gradle.JarCount;

plugins {
    id("application")
    id("my-java-library")
}

tasks.register<JarCount>("countJars") {
    group = "My group";
    description = "Counts!"
    allJars.from(tasks.jar)
    allJars.from(configurations.runtimeClasspath)

    countFile.set(layout.buildDirectory.file("gen/count.txt"));
}


tasks.register<Zip>("bundle"){

    group = "My Group"
    description = "package it all!"

    from(tasks.jar) // output from the last task.
    from(configurations.runtimeClasspath) // Dependencies jars

    destinationDirectory.set(layout.buildDirectory.dir("distribution"))
}

tasks.build {
    dependsOn(tasks.named("bundle"))
}

tasks.register("buildAll") {
    description = "Build even more!"

    dependsOn(tasks.build)
    dependsOn(tasks.named("countJars"))
}