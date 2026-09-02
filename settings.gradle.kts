pluginManagement {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
}

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    maven("https://repo.gradle.org/gradle/libs-releases")
  }
}

rootProject.name = "compose-preview-xr"

include(":renderer-xr")
project(":renderer-xr").projectDir = file("renderers/xr")

include(":samples:xr-spatial")
