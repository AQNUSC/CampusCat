// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // Hilt
    id("com.google.dagger.hilt.android") version "2.57.1" apply false

    // Kotlin
    kotlin("android") version "2.2.0" apply false

    // Kotlin Symbol Processing
    id("com.google.devtools.ksp") version "2.2.20-2.0.3" apply false
}