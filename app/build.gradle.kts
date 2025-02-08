plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.example.helloandroidagain"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.classic.helloandroidagain"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.work)
    implementation(libs.google.location)
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
    implementation(libs.dagger.hilt)
    implementation(libs.androidx.hilt.work)
    testImplementation(libs.junit)
    kapt(libs.dagger.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}