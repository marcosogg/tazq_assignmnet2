plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsCompose) apply false

    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.kotlin.android.ksp) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}