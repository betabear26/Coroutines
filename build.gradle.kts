plugins {
    kotlin("jvm") version "1.5.21"
}

group = "dev.sandeepsuman.coroutines"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1-native-mt")
    implementation("com.google.code.gson:gson:2.8.8")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
}
