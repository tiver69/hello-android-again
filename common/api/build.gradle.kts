plugins {
    id("kotlin")
    id("org.jetbrains.kotlin.jvm")
    kotlin("kapt")
}

kotlin {
    jvmToolchain(17)
}

dependencies{
    api("com.squareup.retrofit2:retrofit:2.10.0")
    implementation("com.google.dagger:hilt-core:2.52")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    kapt("com.google.dagger:hilt-compiler:2.52")
}