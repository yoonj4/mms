
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

  static Future<bool> sendVideo(
      final String videoFilePath,
      final List<String> recipientNumbers) {
    assert(videoFilePath != null);
    assert(recipientNumbers != null);
    assert(recipientNumbers.isNotEmpty);
    return MmsPlatform.instance.sendVideo(videoFilePath, recipientNumbers);
  }
}
// example before step 3: https://medium.com/flutter/how-to-write-a-flutter-web-plugin-part-2-afdddb69ece6
// https://developer.android.com/reference/android/telephony/SmsManager?fbclid=IwAR32iB0aWcgdmfAiD0MXGvitqM2TtzKayFXkPjxi6DaCIehJgjw3Y6QZz7g#sendMultimediaMessage(android.content.Context,%20android.net.Uri,%20java.lang.String,%20android.os.Bundle,%20android.app.PendingIntent)
// https://forums.xamarin.com/discussion/181087/xamarin-android-smsmanager-sendmultimediamessage-mms?fbclid=IwAR1vQxAxIV-hjxASjpsYOeE55tjpfU5zAvtAGhhXqjh8NluRyylVRHldgnQ