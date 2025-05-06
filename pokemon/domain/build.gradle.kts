plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("com.google.dagger:hilt-core:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
}