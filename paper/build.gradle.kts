plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.gradleupShadow)
    alias(libs.plugins.runPaperTask)
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.paper.api)
    compileOnly(libs.kotlin.stdlib)
}

val targetJavaVersion = 21
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks {

    runServer {
        minecraftVersion(libs.versions.paper.server.get())
    }

    // output jar with shaded libs
    shadowJar {
        archiveBaseName = "NameGuard-Paper"
        archiveClassifier = ""
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
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}