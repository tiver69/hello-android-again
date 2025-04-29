plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        namespace = "com.thefork.challenge.search"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("io.coil-kt:coil:2.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    implementation(project(":api"))

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.17")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
}
