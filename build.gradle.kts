plugins {
    alias(libs.plugins.kotlin) apply false
}

allprojects {

    group = "one.meza.envizar"
    version = "1.5.0"

    repositories {
        mavenCentral()
        maven {
            url = uri("https://libraries.minecraft.net")
        }
        maven("https://libraries.minecraft.net")
        maven("https://repo.codemc.org/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "papermc-repo"
        }
    }

}