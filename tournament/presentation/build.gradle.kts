plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.args)
    alias(libs.plugins.kotlin.kapt)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.example.helloandroidagain.tournament.presentation"
}

dependencies {

    implementation(project(":core"))
    implementation(project(":tournament:domain"))

    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.recyclerview)
    implementation(libs.bumptech.glide)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.androidx.browser)
    kapt(libs.bumptech.glide.compiler)

    testImplementation(libs.androidx.junit)
    testImplementation(libs.robolectric)
    testImplementation(libs.dagger.hilt.test)
}