import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl` //id("kotlin-dsl") for some implementation reason in Gradle you need to apply the plugin like this.
                 // This is just for kotlin-dsl plugin. Hopefully a future Gradle version will also allow to write it
                 // with the id("") notation for consistenty
}


dependencies {
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
}