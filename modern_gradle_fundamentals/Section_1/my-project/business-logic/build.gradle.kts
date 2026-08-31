plugins {
    id("my-java-library")
}


dependencies {
    implementation(project(":data-model"))
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.slf4j:slf4j-api:1.7.36")

    //api("") --> dependency is transitively visible as compilation

    //runtimeOnly("group:name") --> only at runtime
    //compileOnly("group:name") --> only at compile time
}

//configurations {
//    compileClasspath // Compile time "view" (aka Variant)
//    runtimeClasspath // Runtime "view" (aka Variant)
//    compileClasspath.extendsFrom(implementation, compileOnly, ...)
//    runtimeClasspath.extendsFrom(implementation, runtimeOnly, ...)
//}