// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.57.1" apply false

    // Kotlin
    alias(libs.plugins.kotlin.android) apply false

    // Kotlin Symbol Processing
    id("com.google.devtools.ksp") version "2.2.20-2.0.3" apply false

    // Jetpack Compose
    alias(libs.plugins.kotlin.compose) apply false
}