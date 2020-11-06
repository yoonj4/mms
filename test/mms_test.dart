import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mms/mms.dart';

void main() {
  const MethodChannel channel = MethodChannel('mms');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await Mms.platformVersion, '42');
  });
}
