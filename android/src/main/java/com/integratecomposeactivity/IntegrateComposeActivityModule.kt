package com.integratecomposeactivity

import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.bridge.*
import android.content.Intent
import android.app.Activity
import android.util.Log

@ReactModule(name = IntegrateComposeActivityModule.NAME)
class IntegrateComposeActivityModule(reactContext: ReactApplicationContext) :
  NativeIntegrateComposeActivitySpec(reactContext) {

  private var activityResultCallback: Callback? = null
  private val activityEventListener = object : ActivityEventListener {
    override fun onActivityResult(
      activity: Activity,
      requestCode: Int,
      resultCode: Int,
      data: Intent?
    ) {
      if (requestCode == REQUEST_CODE) {
        handleActivityResult(resultCode, data)
      }
    }

    override fun onNewIntent(intent: Intent) {}



  }

  init {
    reactContext.addActivityEventListener(activityEventListener)
  }

  override fun getName(): String {
    return NAME
  }

  override fun launchNativeActivity(data: String?, callback: Callback?) {
    Log.d(TAG, "Launching native activity with data: $data")

    val activity = reactApplicationContext.currentActivity
    if (activity == null) {
      callback?.invoke(null, "No current activity available")
      return
    }

    if (callback == null) {
      Log.e(TAG, "Callback is null")
      return
    }

    // Store the callback to use when activity returns result
    activityResultCallback = callback

    try {
      val intent = Intent(activity, MessageBridgeActivity::class.java).apply {
        putExtra("input_data", data ?: "")
      }
      activity.startActivityForResult(intent, REQUEST_CODE)
    } catch (e: Exception) {
      Log.e(TAG, "Error launching activity", e)
      callback.invoke(null, e.message)
      activityResultCallback = null
    }
  }

  private fun handleActivityResult(resultCode: Int, data: Intent?) {
    val callback = activityResultCallback
    activityResultCallback = null

    if (callback == null) {
      Log.e(TAG, "No callback available for activity result")
      return
    }

    when (resultCode) {
      Activity.RESULT_OK -> {
        val result = data?.getStringExtra("result_data") ?: ""
        Log.d(TAG, "Activity returned success with result: $result")
        callback.invoke(result, null)
      }
      Activity.RESULT_CANCELED -> {
        Log.d(TAG, "Activity was cancelled")
        callback.invoke(null, "Activity was cancelled")
      }
      else -> {
        Log.d(TAG, "Activity returned with unknown result code: $resultCode")
        callback.invoke(null, "Unknown result code: $resultCode")
      }
    }
  }

  override fun invalidate() {
    reactApplicationContext.removeActivityEventListener(activityEventListener)
    activityResultCallback = null
  }
  companion object {
    const val NAME = "IntegrateComposeActivity"
    const val REQUEST_CODE = 1001
    const val TAG = "NativeActivityModule"
  }
}
