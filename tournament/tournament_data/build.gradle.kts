plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.helloandroidagain.tournament_data"
}

dependencies {

    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    implementation(libs.google.gson)
    implementation(libs.retrofit)
    kapt(libs.androidx.room.compiler)
    implementation(project(":tournament:tournament_domain"))
}