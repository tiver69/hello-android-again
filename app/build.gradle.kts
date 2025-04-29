plugins {
    id("com.android.application")
    id("kotlin-android")
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
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
    implementation("androidx.appcompat:appcompat:1.7.0")

    implementation(project(":search"))
}
