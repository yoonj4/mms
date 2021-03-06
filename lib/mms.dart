
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:mms_platform_interface/mms_platform_interface.dart';

class Mms {
  static const MethodChannel _channel =
      const MethodChannel('mms');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  void sendVideo(
      final String videoFilePath,
      final List<String> recipientNumbers) {
    assert(videoFilePath != null);
    assert(recipientNumbers != null);
    assert(recipientNumbers.isNotEmpty);
    MmsPlatform.instance.sendVideo(videoFilePath, recipientNumbers);
  }

  void sendVideoWithDefaultApp(
      final String message,
      final String videoFilePath,
      final List<String> recipientNumbers) {
    assert(videoFilePath != null);
    assert(recipientNumbers != null);
    assert(recipientNumbers.isNotEmpty);
    MmsPlatform.instance.sendVideoWithDefaultApp(message, videoFilePath, recipientNumbers);
  }
}