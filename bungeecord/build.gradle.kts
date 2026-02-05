plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleupShadow)
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.bungeecord.api)
    compileOnly(libs.kotlin.stdlib)

    implementation(libs.adventure.api)
    implementation(libs.adventure.minimessage)
    implementation(libs.adventure.bungeecord)
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {

    // output jar with shaded libs
    shadowJar {
        archiveBaseName = "NameGuard-Bungee"
        archiveClassifier = ""
        val shadowPath = "cc.dstm.nameGuard.shadow"
        relocate("net.kyori", "$shadowPath.net.kyori")
    }
    jar { enabled = false }
    build { dependsOn("shadowJar") }

    // process replacements
    processResources {
        val props = mapOf(
            "version" to version,
            "kotlinVersion" to libs.versions.kotlin.get()
        )
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("bungee.yml") {
            expand(props)
        }
    }
}