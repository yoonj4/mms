import Flutter
import UIKit

public class SwiftMmsPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "mms", binaryMessenger: registrar.messenger())
    let instance = SwiftMmsPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if (call.method == "sendVideoWithDefaultApp") {
        let arguments = call.arguments as! Dictionary<String, AnyObject>
        let message = arguments["message"] as? String
        let videoFilePath = arguments["videoFilePath"] as! String
        let recipientNumbers = arguments["recipientNumbers"] as! [String]
        Mms().sendVideoWithDefaultApp(message, videoFilePath, recipientNumbers)
    } else {
        result("iOS " + UIDevice.current.systemVersion)
    }
  }
}
