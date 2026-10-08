package com.example.samplexrspatial

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.core.app.ApplicationProvider
import androidx.xr.runtime.Config
import androidx.xr.runtime.DeviceTrackingMode
import androidx.xr.runtime.Session
import androidx.xr.runtime.SessionCreateSuccess
import androidx.xr.runtime.math.Pose
import org.robolectric.Shadows.shadowOf

/**
 * Brings up the offline XR [Session] the sample's pose tests compose against: the same seam
 * `:renderer-xr`'s `FakeXrHeadPose` uses, inlined so the sample test stays free of a renderer
 * dependency.
 *
 * Since the `androidx.xr.*` 1.0.0-rc01 runtime set, two things that used to work no longer do:
 * - Letting `Subspace` create the session lazily from inside composition leaves the fake runtimes
 *   un-laid-out (every panel size reads 0). Pre-creating it here, before `setContent`, and handing
 *   it over through the `compose_xr_session` decor-view tag that `getOrCreateSession` reads first
 *   restores the layout.
 * - `Session.configure` marks the session configured, which starts its update loop. That loop holds
 *   the session's configuration mutex across `FakePerceptionRuntime.update()`, which blocks from
 *   its second call on, so the mutex is never released: the activity's `ON_PAUSE` is deferred
 *   behind it, and `ON_DESTROY` then destroys the fake perception runtime while it is still
 *   `RESUMED` (`Check failed` in `FakePerceptionRuntime.destroy`). Setting `Session.config`
 *   directly enables device tracking without starting that loop.
 *
 * Call **before** `setContent`.
 */
internal object OfflineXrSession {

  /** Reports the spatial system feature, which selects `Subspace`'s spatial path. */
  fun enableSpatial() {
    val pm = ApplicationProvider.getApplicationContext<Context>().packageManager
    shadowOf(pm).setSystemFeature("android.software.xr.api.spatial", true)
  }

  /** Enables the spatial feature and installs a pre-created offline session on [rule]. */
  fun install(rule: AndroidComposeTestRule<*, ComponentActivity>): Session {
    enableSpatial()
    val created = Session.create(rule.activity)
    check(created is SessionCreateSuccess) { "Could not create offline XR Session: $created" }
    val session = created.session
    rule.activity.window.decorView.setTag(androidx.xr.compose.R.id.compose_xr_session, session)
    return session
  }

  /**
   * [install]s the session with device tracking enabled and the fake `ArDevice` seeded to
   * [headPose], so `rotateToLookAtUser` resolves a viewer. The arcore types are reached
   * reflectively so the sample only needs the `arcore-testing` artifact on its test classpath.
   */
  fun installWithHeadPose(
    rule: AndroidComposeTestRule<*, ComponentActivity>,
    headPose: Pose,
  ): Session {
    val session = install(rule)
    Session::class
      .java
      .getMethod("access\$setConfig\$p", Session::class.java, Config::class.java)
      .invoke(null, session, Config(deviceTracking = DeviceTrackingMode.SPATIAL_LAST_KNOWN))

    val arDeviceClass = Class.forName("androidx.xr.arcore.ArDevice")
    val arDevice = arDeviceClass.getMethod("getInstance", Session::class.java).invoke(null, session)
    val runtimeArDevice = arDeviceClass.getMethod("getRuntimeArDevice\$arcore").invoke(arDevice)
    runtimeArDevice.javaClass
      .getMethod("setDevicePose", Pose::class.java)
      .invoke(runtimeArDevice, headPose)
    val runtimeTrackingClass = Class.forName("androidx.xr.arcore.runtime.TrackingState")
    runtimeArDevice.javaClass
      .getMethod("setTrackingState", runtimeTrackingClass)
      .invoke(runtimeArDevice, runtimeTrackingClass.getField("TRACKING").get(null))

    // With the update loop off, nothing copies the runtime device into ArDevice.state; prime the
    // StateFlow the RotateToLookAtUserNode collects directly.
    val trackingStateClass = Class.forName("androidx.xr.arcore.TrackingState")
    val stateClass = Class.forName("androidx.xr.arcore.ArDevice\$State")
    val state =
      stateClass
        .getConstructor(Pose::class.java, trackingStateClass, arDeviceClass)
        .newInstance(headPose, trackingStateClass.getField("TRACKING").get(null), arDevice)
    val stateField = arDeviceClass.getDeclaredField("_state").apply { isAccessible = true }
    @Suppress("UNCHECKED_CAST")
    (stateField.get(arDevice) as kotlinx.coroutines.flow.MutableStateFlow<Any?>).value = state
    return session
  }
}
