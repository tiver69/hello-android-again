import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.androidx.navigation.args)
}

val keystoreProperties = Properties()
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
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

        testInstrumentationRunner = "com.example.helloandroidagain.runner.HiltTestRunner"

        buildConfigField(
            "String",
            "UNSPLASH_CLIENT_ID",
            "\"${project.properties["UNSPLASH_CLIENT_ID"]}\""
        )
    }
    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = keystoreProperties["storeFile"]?.let { file(it) }
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    buildFeatures {
        buildConfig = true
    }

    flavorDimensionList.add("enviroment")
    productFlavors {
        create("stage") {
            dimension = "enviroment"
            resValue("string", "app_name", "Hello Android Stage")
            buildConfigField("String", "UNSPLASH_BASE_URL", "\"https://api.unsplash.com/\"") // some test endpoint can be used
        }
        create("prod") {
            dimension = "enviroment"
            resValue("string", "app_name", "Hello Android Prod")
            buildConfigField("String", "UNSPLASH_BASE_URL", "\"https://api.unsplash.com/\"")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            firebaseCrashlytics {
                mappingFileUploadEnabled = true
            }
            signingConfig = signingConfigs.getByName("release")
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
    viewBinding {
        enable = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        packaging {
            resources.excludes.add("META-INF/*")
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }
}

ktlint {
    android = true
    disabledRules.addAll(
        "final-newline",
        "no-wildcard-imports",
        "max-line-length",
        "import-ordering",
        "trailing-comma-on-declaration-site",
        "trailing-comma-on-call-site",
    )
    tasks.matching {
        it.name in listOf("ktlintAndroidTestSourceSetCheck", "ktlintTestSourceSetCheck")
    }.configureEach { enabled = false }
    reporters {
        reporter(ReporterType.PLAIN)
    }
}

dependencies {

    implementation(project(":auth:auth-presentation"))
    implementation(project(":core"))
    implementation(project(":tournament:tournament_presentation"))
    implementation(project(":tournament:tournament_domain"))
    implementation(project(":tournament:tournament_data"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.espresso.idling.resource)
    implementation(libs.dagger.hilt)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    kapt(libs.dagger.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.robolectric)
    testImplementation(libs.dagger.hilt.test)
    testImplementation(libs.androidx.navigation.ui)
    kaptTest(libs.dagger.hilt.compiler)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.dagger.hilt.test)
    androidTestImplementation(libs.androidx.navigation.ui)
    kaptAndroidTest(libs.dagger.hilt.compiler)
}

tasks.register<Copy>("applyGitHooks") {
    description = "Copy git hooks from the scripts to the .git/hooks folder."
    group = "git hooks"
    outputs.upToDateWhen { false }
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks/")
}
tasks.build {
    dependsOn("applyGitHooks")
}