plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    compileSdk = 35

    defaultConfig {
        applicationId = "com.thefork.challenge"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        namespace = "com.thefork.challenge"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":search"))
    implementation(project(":pokemon:presentation"))
    implementation(project(":pokemon:data"))
    implementation(project(":pokemon:domain"))

    implementation("androidx.appcompat:appcompat:1.6.1")

    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
}
