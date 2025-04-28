plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.helloandroidagain.tournament.data"
}

dependencies {

    implementation(project(":core"))
    implementation(project(":tournament:domain"))

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.google.gson)
    implementation(libs.retrofit)
    kapt(libs.androidx.room.compiler)
}