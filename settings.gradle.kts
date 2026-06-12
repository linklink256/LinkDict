pluginManagement {
    repositories {
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

rootProject.name = "LinkDict"

include(":app")
include(":core:common")
include(":core:model")
include(":core:ui")
include(":core:designsystem")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core")core(includedata:dictionary")
include(":feature:search")
include(":feature:dict-manager")
