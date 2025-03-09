// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    // Add the dependency for the Hilt Gradle plugin
    id("com.google.dagger.hilt.android") version "2.48" apply false
    // Add the dependency for the Kotlin Symbol Processing Gradle plugin
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false

}