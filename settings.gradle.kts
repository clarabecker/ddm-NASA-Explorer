pluginManagement {
    repositories {
        // Estas linhas dizem ao Gradle para procurar plugins
        // nos repositórios do Google, Maven e Gradle.
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ddmNASAExplorer"
include(":app")