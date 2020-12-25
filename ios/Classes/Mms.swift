//
//  Mms.swift
//  mms
//
//  Created by John Smith on 12/12/20.
//

import Foundation
import MessageUI

public class Mms: UIViewController, MFMessageComposeViewControllerDelegate {
    public func messageComposeViewController(_ controller: MFMessageComposeViewController, didFinishWith result: MessageComposeResult) {
        
    }
    
    public func sendVideoWithDefaultApp(
        _ message: String?,
        _ videoFilePath: String,
        _ recipientNumbers: [String]) {
        
        if MFMessageComposeViewController.canSendText() {
            let composeVC = MFMessageComposeViewController()
            composeVC.messageComposeDelegate = self
             
            // Configure the fields of the interface.
            composeVC.recipients = recipientNumbers
            composeVC.body = message
             
            // Present the view controller modally.
            self.present(composeVC, animated: true, completion: nil)
        }
    }
}
