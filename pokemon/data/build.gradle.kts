plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(17)
}

dependencies{
    implementation(project(":common:api"))
    implementation(project(":pokemon:domain"))

    implementation("com.google.dagger:hilt-core:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
}