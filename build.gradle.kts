plugins {
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.compose.compiler) apply false
  alias(libs.plugins.ktfmt) apply false
  alias(libs.plugins.maven.publish) apply false
  alias(libs.plugins.compose.preview) apply false
}

tasks.register("check") {
  group = "verification"
  dependsOn(":renderer-xr:check", ":samples:xr-spatial:testDebugUnitTest")
}

tasks.register("ktfmtCheck") {
  group = "verification"
  dependsOn(":renderer-xr:ktfmtCheck", ":samples:xr-spatial:ktfmtCheck")
}

tasks.register("ktfmtFormat") {
  group = "formatting"
  dependsOn(":renderer-xr:ktfmtFormat", ":samples:xr-spatial:ktfmtFormat")
}

