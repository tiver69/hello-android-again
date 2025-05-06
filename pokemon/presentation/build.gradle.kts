plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
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
    implementation(project(":pokemon:domain"))
    implementation(project(":common:navigation"))

    implementation("androidx.core:core-ktx:1.16.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2024.08.00"))
    implementation("androidx.compose.material3:material3-android:1.3.2")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:1.9.1")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.1.0")
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
}