@file:Suppress("DEPRECATION")

import com.vanniktech.maven.publish.AndroidSingleVariantLibrary
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.SourcesJar

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.maven.publish)
}

group = "ee.schimke.composeai"

version = providers.environmentVariable("XR_VERSION").orElse("2.0.0-SNAPSHOT").get()

ktfmt { googleStyle() }

android {
  namespace = "ee.schimke.composeai.renderer.xr"
  compileSdk = 37

  defaultConfig { minSdk = 24 }

  buildFeatures { compose = true }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      all {
        it.jvmArgs("-Xmx2048m")
        it.systemProperty("robolectric.graphicsMode", "NATIVE")
        it.systemProperty("robolectric.looperMode", "PAUSED")
        it.systemProperty("robolectric.conscryptMode", "OFF")
        it.systemProperty("robolectric.pixelCopyRenderMode", "hardware")
        it.systemProperty("roborazzi.test.record", "true")
      }
    }
  }

  lint {
    // The offline fake-runtime seam deliberately calls Jetpack XR APIs restricted to its own
    // library group. These are the same calls covered by the Robolectric tests below.
    disable += "RestrictedApi"
  }
}

dependencies {
  api(libs.preview.data.api)
  implementation(libs.data.render.core)
  compileOnly(libs.data.layoutinspector.connector)

  compileOnly(platform(libs.compose.bom))
  compileOnly(libs.compose.ui)
  compileOnly(libs.compose.foundation)
  compileOnly(libs.activity.compose)
  compileOnly(libs.xr.compose)
  compileOnly(libs.xr.compose.testing)
  compileOnly(libs.compose.ui.test.junit4)
  compileOnly(libs.robolectric)
  compileOnly(libs.junit)
  compileOnly(libs.roborazzi)
  compileOnly(libs.roborazzi.compose)

  testImplementation(platform(libs.compose.bom))
  testImplementation(libs.compose.ui)
  testImplementation(libs.compose.foundation)
  testImplementation(libs.activity.compose)
  testImplementation(libs.data.layoutinspector.connector)
  testImplementation(libs.xr.compose)
  testImplementation(libs.xr.compose.testing)
  testImplementation(libs.compose.ui.test.junit4)
  testImplementation(libs.compose.ui.test.manifest)
  testImplementation(libs.xr.runtime.testing)
  testImplementation(libs.xr.scenecore.testing)
  testImplementation(libs.xr.arcore.testing)
  testImplementation(libs.robolectric)
  testImplementation(libs.roborazzi)
  testImplementation(libs.roborazzi.compose)
  testImplementation(libs.junit)
  testImplementation(libs.truth)
}

mavenPublishing {
  publishToMavenCentral(automaticRelease = true)
  if (!version.toString().endsWith("SNAPSHOT")) signAllPublications()
  configure(
    AndroidSingleVariantLibrary(
      javadocJar = JavadocJar.Empty(),
      sourcesJar = SourcesJar.Sources(),
      variant = "release",
    )
  )
  coordinates(group.toString(), "renderer-xr", version.toString())
  pom {
    name.set("Compose Preview — XR Renderer")
    description.set(
      "Offline Compose XR producer of SpatialScene layouts and per-panel textures for compose-preview."
    )
    url.set("https://github.com/yschimke/compose-preview-xr")
    inceptionYear.set("2025")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("repo")
      }
    }
    developers {
      developer {
        id.set("yschimke")
        name.set("Yuri Schimke")
        url.set("https://github.com/yschimke")
      }
    }
    scm {
      url.set("https://github.com/yschimke/compose-preview-xr")
      connection.set("scm:git:https://github.com/yschimke/compose-preview-xr.git")
      developerConnection.set("scm:git:ssh://git@github.com/yschimke/compose-preview-xr.git")
    }
  }
}
