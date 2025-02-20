plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
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

    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            buildConfigField("String", "UNSPLASH_CLIENT_ID", "\"${project.properties["UNSPLASH_CLIENT_ID"]}\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "UNSPLASH_CLIENT_ID", "\"${project.properties["UNSPLASH_CLIENT_ID"]}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    lint {
        lintConfig = file("$rootDir/android_glide_lint.xml")
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
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.browser)
    implementation(libs.google.gson)
//    implementation(libs.rx.java)
//    implementation(libs.rx.android)
    implementation(libs.coroutines)
    implementation(libs.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.bumptech.glide)
    implementation(libs.dagger.hilt)
    kapt(libs.bumptech.glide.compiler)
    kapt(libs.dagger.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}