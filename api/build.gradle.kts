plugins {
    id("kotlin")
}

dependencies {
    api("com.squareup.retrofit2:retrofit:2.10.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}

kotlin {
    jvmToolchain(17)
}
