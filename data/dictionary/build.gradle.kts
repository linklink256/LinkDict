plugins {
    alias(libs.plugins.kotlin.jvm)
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":core:dictionary-api"))
    implementation(project(":core:model"))
    implementation(project(":domain:dictionary"))
    implementation(libs.kotlinx.coroutines.core)
}
