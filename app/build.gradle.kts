plugins {
    alias(libs.plugins.android.application)

    // Hilt
    id("com.google.dagger.hilt.android")

    // Kotlin
    id("org.jetbrains.kotlin.android")

    // Kotlin Symbol Processing
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.catboy.campuscat"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.catboy.campuscat"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "0.0.1-beta"

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
}

dependencies {

    // Android Core
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}