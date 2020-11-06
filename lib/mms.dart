
import 'dart:async';

import 'package:flutter/services.dart';

class Mms {
  static const MethodChannel _channel =
      const MethodChannel('mms');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
