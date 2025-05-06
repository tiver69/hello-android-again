plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android") version "2.52"
    kotlin("kapt")
}

android {
    namespace = "com.thefork.challenge.pokemon.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        namespace = "com.thefork.challenge.pokemon.presentation"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
}