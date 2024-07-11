buildscript {
    dependencies {
        classpath(libs.google.services)
        classpath ("com.google.gms:google-services:4.4.2" )
        classpath("com.github.bumptech.glide:glide:4.12.0")


    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.


plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id ("androidx.navigation.safeargs.kotlin") version "2.7.7" apply false
    id ("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false



}