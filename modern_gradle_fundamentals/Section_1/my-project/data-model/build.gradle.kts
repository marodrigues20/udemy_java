
plugins {
    id("my-java-library")
}

dependencies {
    // 1. Imports the catalog/version rules (does not download JARs)
    implementation(platform("com.example:platform"))
}