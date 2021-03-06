package com.yoonj4.mms

import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** MmsPlugin */
class MmsPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  @NonNull private lateinit var mms : Mms

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "mms")
    channel.setMethodCallHandler(this)

    mms = Mms(flutterPluginBinding.applicationContext)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if (call.method == "sendVideo") {
      val videoFilePath = call.argument<String>("videoFilePath")
      val recipientNumbers = call.argument<List<String>>("recipientNumbers")

      if (videoFilePath != null && recipientNumbers != null) {
        mms.sendVideo(videoFilePath, recipientNumbers)
      } else {
        result.error(
                "InvalidArguments",
                "videoFilePath and recipientNumbers must be non-null",
                null
        )
      }
    } else if (call.method == "sendVideoWithDefaultApp") {
      val message = call.argument<String>("message")
      val videoFilePath = call.argument<String>("videoFilePath")
      val recipientNumbers = call.argument<List<String>>("recipientNumbers")

      if (videoFilePath != null && recipientNumbers != null) {
        mms.sendVideoWithDefaultApp(message, videoFilePath, recipientNumbers)
      } else {
        result.error(
                "InvalidArguments",
                "videoFilePath and recipientNumbers must be non-null",
                null
        )
      }
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
