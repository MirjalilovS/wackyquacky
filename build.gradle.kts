// Root project holds no code; shared settings for all modules live here.
plugins {
    alias(libs.plugins.spring.boot) apply false
}

subprojects {
    apply(plugin = "java")

    group = "com.wackyquacky"
    version = "0.1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://jitpack.io") // chesslib is only published to JitPack
    }

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion = JavaLanguageVersion.of(21)
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
