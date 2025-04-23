plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.helloandroidagain.tournament_domain"
}
