plugins {
    // Transforms the current module into a Gradle platform responsible only for managing and publishing dependency
    // versions (without producing a .jar file).
    id("java-platform") // Similar to BOM == Bill of Material
}

group = "com.example"

// Allows the platform to declare and import other external platforms or BOMs inside the dependencies block
javaPlatform.allowDependencies()

dependencies {
    // Re-exports the Jackson BOM within your platform
    api(platform("com.fasterxml.jackson:jackson-bom:2.13.3"))
}

// Manually defines exact versions for libraries not coming from Jackson (commons-lang3, slf4j, etc.).
// Does not add the JAR to the project. It only specifies the recommended/forced version.
dependencies.constraints {
    api("org.apache.commons:commons-lang3:3.12.0")
    api("org.slf4j:slf4j-api:1.7.36")
    api("org.slf4j:slf4j-simple:1.7.36")
}

// Practical effect for other modules:
// By declaring implementation(platform(project("com.example:platform"))) in the :business-logic or :app module, the project
// gains automatic access to both the versions defined in dependencies.constraints and all libraries managed
// by jackson-bom:2.13.3, without needing to specify a version tag for any of them.