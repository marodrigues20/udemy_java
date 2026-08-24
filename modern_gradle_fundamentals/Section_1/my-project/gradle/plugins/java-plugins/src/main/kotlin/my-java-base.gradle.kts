// Plugin are just a way to share configuration among several places
plugins {
    id("java")
    id("com.diffplug.spotless")
}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

//tasks.withType(JavaCompile::class).configureEach{
//    options.encoding = "UTF-8"
//}

//tasks.named<JavaCompiler>("compileJava") {} // == tasks.compileJava { }

//tasks.compileTestJava { }

//tasks.test { }

//tasks.javadoc{ }