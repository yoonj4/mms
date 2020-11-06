#import "MmsPlugin.h"
#if __has_include(<mms/mms-Swift.h>)
#import <mms/mms-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "mms-Swift.h"
#endif

@implementation MmsPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMmsPlugin registerWithRegistrar:registrar];
}
@end
