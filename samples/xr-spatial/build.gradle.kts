plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.compose.preview)
}

ktfmt { googleStyle() }

configurations.configureEach {
  resolutionStrategy.dependencySubstitution {
    substitute(module("ee.schimke.composeai:renderer-xr")).using(project(":renderer-xr"))
    // A source-substituted plugin has a SNAPSHOT PluginVersion, but this repository does not own
    // renderer-android. Keep that sibling on the released compose-preview line during cross-repo
    // development.
    substitute(module("ee.schimke.composeai:renderer-android"))
      .using(module("ee.schimke.composeai:renderer-android:${libs.versions.compose.preview.get()}"))
  }
}

composePreview {
  sdkVersion.set(35)
  enableXrPreviews.set(true)
}

android {
  namespace = "com.example.samplexrspatial"
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
      all { it.jvmArgs("-Xmx2048m") }
    }
  }
}

dependencies {
  implementation(platform(libs.compose.bom))
  implementation(libs.compose.ui)
  implementation(libs.compose.material3)
  implementation(libs.compose.ui.tooling.preview)
  implementation(libs.compose.foundation)
  implementation(libs.xr.compose)
  implementation(libs.preview.annotations)

  debugImplementation(libs.compose.ui.tooling)

  testImplementation(libs.robolectric)
  testImplementation(libs.compose.ui.test.junit4)
  testImplementation(libs.compose.ui.test.manifest)
  testImplementation(libs.xr.compose.testing)
  testImplementation(libs.xr.runtime.testing)
  testImplementation(libs.xr.scenecore.testing)
  testImplementation(libs.xr.arcore.testing)
  testImplementation(libs.junit)
  testImplementation(libs.truth)
}
