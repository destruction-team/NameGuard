rootProject.name = "NameGuard"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }
}

include("core", "bungeecord", "paper")