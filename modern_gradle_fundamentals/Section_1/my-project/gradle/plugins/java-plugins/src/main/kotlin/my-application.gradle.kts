plugins {
    id("application")
    id("my-java-library")
}


tasks.register<Zip>("bundle"){

    group = "My Group"
    description = "package it all!"

    from(tasks.jar) // output from the last task.
    from(configurations.runtimeClasspath) // Dependencies jars

    destinationDirectory.set(layout.buildDirectory.dir("distribution"))
}