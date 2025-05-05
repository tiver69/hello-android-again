plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
}

kotlin {
    jvmToolchain(17)
}

dependencies{
    implementation(project(":api"))
    implementation(project(":pokemon:domain"))
}