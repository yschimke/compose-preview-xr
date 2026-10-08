// rotateToLookAtUser was marked @ExperimentalRotateToLookAtUserApi (opt-in, error-level) in
// androidx.xr.compose 1.0.0-alpha15; this test exercises it, so opt in file-wide rather than at
// each call site (mirrors XrModifierPreviews.kt).
@file:OptIn(ExperimentalRotateToLookAtUserApi::class)

package com.example.samplexrspatial

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialBox
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.ExperimentalRotateToLookAtUserApi
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.offset
import androidx.xr.compose.subspace.layout.rotateToLookAtUser
import androidx.xr.compose.subspace.layout.width
import androidx.xr.compose.subspace.semantics.testTag
import androidx.xr.compose.testing.onSubspaceNodeWithTag
import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Vector3
import com.google.common.truth.Truth.assertThat
import kotlin.math.abs
import kotlin.math.sqrt
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

/**
 * Proves the **`rotateToLookAtUser`** ("face the viewer" / billboard) `SubspaceModifier` works
 * under the offline fake XR runtime — the same recovery path `SubspaceSceneRecorder` drives for the
 * [RotateToLookAtUserPreview] `@XrSubspacePreview`.
 *
 * `rotateToLookAtUser` is driven by `RotateToLookAtUserNode`, which reads the user's head pose from
 * an ARCore `ArDevice` perception job. Out of the box offline the default `Session` is created with
 * device tracking `DISABLED`, so the node never initialises `arDevice` and crashes in its head-pose
 * job (`UninitializedPropertyAccessException`). [OfflineXrSession.installWithHeadPose] uses the
 * fake perception runtime (registered via `ServiceLoader`, see `src/test/resources/META-INF/
 * services`), enables device tracking, and exercises a **custom viewer head pose in front of the
 * panels (+Z)** — so a centred panel ends up facing the viewer (≈ identity) and side panels turn
 * inward toward them. The render path's camera-derived default pose is covered in `:renderer-xr`'s
 * `XrSubspaceRendererTest`.
 *
 * Its own class (own Robolectric sandbox + PAUSED looper, the mode `:renderer-xr`'s render task
 * runs in), which keeps the perception runtime it seeds from leaking into the plainer
 * `SubspaceModifierPoseTest` cases.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
@LooperMode(LooperMode.Mode.PAUSED)
class RotateToLookAtUserPoseTest {

  @Suppress("DEPRECATION") @get:Rule val rule = createAndroidComposeRule<ComponentActivity>()

  /** A unit quaternion's rotation angle in degrees: `2·acos(|w|)`. */
  private fun angleDeg(x: Float, y: Float, z: Float, w: Float): Double {
    val vlen = sqrt((x * x + y * y + z * z).toDouble())
    return Math.toDegrees(2.0 * Math.atan2(vlen, abs(w).toDouble()))
  }

  @Test
  fun rotateToLookAtUserFacesViewerOffline() {
    // Enable device tracking + seed a head pose in front of the panels so "look at user" resolves.
    OfflineXrSession.installWithHeadPose(rule, Pose(translation = Vector3(0f, 0f, 2f)))
    rule.setContent {
      Subspace {
        SpatialBox {
          // Centred panel: already in front of the viewer, so it should face straight on (≈
          // identity).
          SpatialPanel(
            SubspaceModifier.testTag("look-center")
              .width(300.dp)
              .height(200.dp)
              .rotateToLookAtUser()
          ) {
            ReferencePanel("center")
          }
          // Offset to the left: must turn toward the viewer (rotate about the vertical Y axis).
          SpatialPanel(
            SubspaceModifier.testTag("look-left")
              .width(300.dp)
              .height(200.dp)
              .offset(x = (-500).dp)
              .rotateToLookAtUser()
          ) {
            ReferencePanel("left")
          }
        }
      }
    }
    // Composes + measures + disposes WITHOUT crashing (the old failure mode was an
    // UninitializedPropertyAccessException in the head-pose job / on disposal).
    rule.waitForIdle()

    val c =
      rule
        .onSubspaceNodeWithTag("look-center")
        .fetchSemanticsNode("no 'look-center'")
        .poseInRoot
        .rotation
    val l =
      rule
        .onSubspaceNodeWithTag("look-left")
        .fetchSemanticsNode("no 'look-left'")
        .poseInRoot
        .rotation
    val cAngle = angleDeg(c.x, c.y, c.z, c.w)
    val lAngle = angleDeg(l.x, l.y, l.z, l.w)
    println("rotateToLookAtUser center -> quat=(${c.x}, ${c.y}, ${c.z}, ${c.w}) angle=$cAngle°")
    println("rotateToLookAtUser left   -> quat=(${l.x}, ${l.y}, ${l.z}, ${l.w}) angle=$lAngle°")

    // Sensible, non-degenerate facing rotations — emphatically NOT the old ~180° Y-flip.
    assertThat(cAngle).isLessThan(90.0)
    assertThat(lAngle).isLessThan(90.0)
    // The centred panel faces the viewer head-on (near identity).
    assertThat(cAngle).isLessThan(5.0)
    // The offset panel actually turns toward the viewer, about the vertical (Y) axis.
    assertThat(lAngle).isGreaterThan(3.0)
    assertThat(abs(l.y)).isGreaterThan(abs(l.x))
    assertThat(abs(l.y)).isGreaterThan(abs(l.z))
  }
}
