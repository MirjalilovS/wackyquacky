// Shared library: domain, analysis and persistence. Not runnable on its own.
plugins {
    `java-library`
}

dependencies {
    api(platform(libs.spring.boot.bom))
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.chesslib)
    runtimeOnly(libs.postgresql)

    testImplementation(libs.spring.boot.starter.test)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
