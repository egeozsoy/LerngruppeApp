//
//  Message.swift
//  ChatApp
//
//  Created by Ege on 18.09.17.
//  Copyright © 2017 Ege Özsoy. All rights reserved.
//

import Foundation
import UIKit

public class Message: NSObject{
    var messagetext:String
    var sender:Bool
    
    var date: String
    
    init(messagetext:String, sender:Bool , date:String) {
        self.messagetext = messagetext
        self.sender = sender
        self.date = date
    }
}
