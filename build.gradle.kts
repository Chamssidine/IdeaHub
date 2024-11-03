// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("room_version", "2.6.1")
    }
}



plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id ("com.google.dagger.hilt.android") version "2.51.1" apply false
    kotlin("kapt") version "2.0.21" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20-Beta2"
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false

}
