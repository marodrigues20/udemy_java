plugins {
    id("my-java-library") // Similar to BOM == Bill of Material
}

dependencies {

    implementation(platform("com.example:platform"))

    implementation(project(":data-model"))
    implementation("org.apache.commons:commons-lang3")
    implementation("org.slf4j:slf4j-api")
}