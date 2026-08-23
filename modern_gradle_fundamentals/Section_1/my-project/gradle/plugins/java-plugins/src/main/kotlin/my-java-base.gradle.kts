// Plugin are just a way to share configuration among several places
plugins {
    id("java")
    id("com.diffplug.spotless")
}

java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}