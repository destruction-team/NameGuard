plugins {
    alias(libs.plugins.kotlin)
}

dependencies {
//    compileOnly(libs.bungeecord.api)
    compileOnly(libs.kotlin.stdlib)

    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
//    implementation(libs.adventure.bungeecord)
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}