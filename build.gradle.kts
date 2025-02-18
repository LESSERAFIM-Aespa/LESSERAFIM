buildscript {
    buildscript {
        repositories {
            mavenCentral()
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.7.3")
        classpath("com.google.gms:google-services:4.4.0")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "1.8.20" apply false
}