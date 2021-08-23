plugins {
    kotlin("jvm") version "1.5.21"
}

group = "dev.sandeepsuman.coroutines"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation(kotlin("stdlib"))
}
