plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        namespace = "com.thefork.challenge.pokemon"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation(platform("androidx.compose:compose-bom:2024.08.00"))
    implementation("androidx.compose.foundation:foundation")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.activity:activity-compose:1.9.1")
}
