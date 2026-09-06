plugins {
    id("my-java-library") // Similar to BOM == Bill of Material
}

dependencies {

    //implementation(platform("com.example:platform"))

    implementation(project(":data-model"))
    // Using Dependency Inversion Catalog
    implementation(libs.commons.lang)
    implementation(libs.slf4j.api)
}