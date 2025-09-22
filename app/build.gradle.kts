import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)

    // Kotlin
    alias(libs.plugins.kotlin.android)

    // Kotlin Symbol Processing
    alias(libs.plugins.ksp)

    // Jetpack Compose
    alias(libs.plugins.kotlin.compose)
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

    buildFeatures {
        // Jetpack Compose 支持
        compose = true

        // BuildConfig
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    compilerOptions  {
        jvmTarget.set(JvmTarget.JVM_11)
    }
}

dependencies {

    // Android Core
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // md3 icon
    implementation(libs.material.icons.core)
    implementation(libs.material.icons.extended)

    // RxJava3
    implementation(libs.rxjava3)
    implementation(libs.rxjava3.android)

    // Lombok
    implementation(libs.lombok)
    annotationProcessor(libs.lombok)

    // Timber
    implementation(libs.timber)
}