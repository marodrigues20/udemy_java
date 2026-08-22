// plugins can be developed myself on top of Gradle Core.
// id("java"), id("java-library") and id("application") are packaged with Grandle
plugins {
    //id("java") // Java core functionality
    id("application") // application already apply "java" on top. That's the reason I commented. The name this is
    // composability of plugins
}

// This configuration block is called an extension in Gradle. Because this was added by the 'java' plugin. So it's an
// extension to the Gradle DSL that the plugin adds.
java{
    toolchain.languageVersion.set(JavaLanguageVersion.of(26))
}