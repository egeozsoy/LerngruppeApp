//
//  ViewController.swift
//  ChatApp
//
//  Created by Ege on 17.09.17.
//  Copyright © 2017 Ege Özsoy. All rights reserved.
//

import UIKit
import CoreData

class ChatApp: UIViewController,UITableViewDelegate, UITableViewDataSource {

//    var messages = [Message]()
//    var messages: [NSManagedObject] = []
//    var messages : 
    
    @IBOutlet weak var messagecontent: UITextView!
    var messagecontentpos: CGPoint!
    @IBOutlet weak var button: UIButton!
    var buttonpos: CGPoint!
    @IBOutlet weak var viewofui: UIView!
    var viewofuipos: CGPoint!
    @IBOutlet weak var tableView: UITableView!
    var imageviewpos:CGPoint!
    @IBOutlet weak var heightofbutton: NSLayoutConstraint!
    var keySize: CGRect!
    
    var managedObjectContext: NSManagedObjectContext!
    var messagesentity: Messages?
    
    @IBOutlet weak var myimageTextTest: UILabel!
        func loadMessages(){
        let date = Date()
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from: date)
        let minutes = calendar.component(.minute, from: date)
        let msg = Message(messagetext: "Hey", sender: true, date: "\(hour):\(minutes)")
        let msg2 = Message(messagetext: "test", sender: false, date: "\(hour):\(minutes)")
        
        messagesentity!.message.append(msg)
        messagesentity!.message.append(msg2)
    }

    
    
    @IBAction func sendMessage(_ sender: Any) {
//        print(<#T##items: Any...##Any#>)
        let date = Date()
//        messagesentity!.message = []
        let calendar = Calendar.current
        let hour = calendar.component(.hour, from: date)
        let minutes = calendar.component(.minute, from: date)
        let msg = Message(messagetext: messagecontent.text, sender: true, date: "\(hour):\(minutes)")
        print("message entity \(messagesentity) ")
        print("message message \(messagesentity!.message)")
        messagesentity!.message.append(msg)
        print("HEY")
        let msg2 = Message(messagetext: "testtest!", sender: false, date: "\(hour):\(minutes)")
        messagesentity!.message.append(msg2)
//        print(messages)
        tableView.reloadData()
        //scroll to the last message
        let path = NSIndexPath(row: messagesentity!.message.count - 1, section: 0)
        print("sendMessage \(messagesentity!.message.count - 1 )")
        print("sendMessage \(path)")
        tableView.scrollToRow(at: path as IndexPath, at: UITableViewScrollPosition.bottom, animated: true)
        messagecontent.text = ""
//        messagesentity!.writing = "ege"
        
        do{
            print("test1")
            try managedObjectContext.save()
            print("saving")
            print("message message \(messagesentity!.message)")
        }
        catch{
            print("test2")
            fatalError("error")
        }
    }
    
    
     func numberOfSections(in tableView: UITableView) -> Int{
        return 1
    }
     func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
               return messagesentity!.message.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell{

        
        
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "TextCell", for: indexPath) as? ChatBubble else{
            fatalError("The dequeued cell is not an instance of MealTableViewCell.")
        }
//        let fullString = NSMutableAttributedString(string: "S")
//        
//        // create our NSTextAttachment
//        let image1Attachment = NSTextAttachment()
//        image1Attachment.image = UIImage(named: "python.png")
//        
//        
//        // wrap the attachment in its own attributed string so we can append it
//        let image1String = NSAttributedString(attachment: image1Attachment)
//        
//        // add the NSTextAttachment wrapper to our full string, then add some more text.
//        fullString.append(image1String)
//        fullString.append(NSAttributedString(string: "End"))
//        
//        // draw the result in a label
//        cell.myImageTextTest.attributedText = fullString
        if imageviewpos == nil {
//            print("was nul")
            imageviewpos = cell.myimage.frame.origin
        }
//  adjust table to text
        tableView.rowHeight = UITableViewAutomaticDimension
        tableView.estimatedRowHeight = 2
        var im = #imageLiteral(resourceName: "SpeechBubble")
        
        cell.myimage.image = im
//        print("height is \(cell.mytext.bounds.size.height) " )
        
//        cell.mytime.text = messages[indexPath.row].date
       
        let txt = messagesentity!.message[indexPath.row].messagetext
        
        if messagesentity!.message[indexPath.row].sender{
            cell.myimage.frame.origin.x = imageviewpos.x
            
            cell.mytext.textAlignment = .right
//            cell.mytime.textAlignment = .right
           
            cell.myimage.transform = CGAffineTransform(scaleX: 1, y: 1)
            
            
            for constraint in cell.contentView.constraints{
                if constraint.identifier == "Trailing"{
                    constraint.constant = 0
                }
                else if constraint.identifier == "Leading"{
                    constraint.constant = 83
            }  else if constraint.identifier == "myTextTrailing"{
                    constraint.constant = 20
                    
                }
                else if constraint.identifier == "myTextLeading"{
                    constraint.constant = 95
                }
            }
        }
        else{
            cell.myimage.frame.origin.x = imageviewpos.x - 80
            cell.mytext.frame.origin.x = imageviewpos.x - 80
            //flip horizontal
            let multiply = Int(cell.mytext.bounds.size.height / 20)
            print(multiply)
            cell.myimage.transform = CGAffineTransform(scaleX: -1, y: 1)
            
            cell.mytext.textAlignment = .left
//            cell.mytime.textAlignment = .left
            for constraint in cell.contentView.constraints{
                if constraint.identifier == "Trailing"{
                    constraint.constant = 83
                }
                else if constraint.identifier == "Leading"{
                    constraint.constant = 0
                }
                else if constraint.identifier == "myTextTrailing"{
                    constraint.constant = 95
                    
                }
                else if constraint.identifier == "myTextLeading"{
                    constraint.constant = 20
                }
            }

        }
                cell.mytext.text = txt
        
        
        
        return cell
    }
    
    func adjustForKeyboard(notification: Notification) {
        
        if let keyboardSize = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue{
            keySize =  keyboardSize
        let userInfo = notification.userInfo!
//                print("here")
                let keyboardScreenEndFrame = (userInfo[UIKeyboardFrameEndUserInfoKey] as! NSValue).cgRectValue
                let keyboardViewEndFrame = view.convert(keyboardScreenEndFrame, from: view.window)
        
                if notification.name == Notification.Name.UIKeyboardWillHide {
                    tableView.contentInset = UIEdgeInsets.zero
                } else {
                    tableView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: keyboardViewEndFrame.height, right: 0)
                }
        
                tableView.scrollIndicatorInsets = tableView.contentInset
                let path = NSIndexPath(row: messagesentity!.message.count - 1, section: 0)
            
            print("adjustKeyboard \(messagesentity!.message.count - 1)")
            print("adjustKeyboard \(path)")
            
                tableView.scrollToRow(at: path as IndexPath, at: UITableViewScrollPosition.bottom, animated: true)
//            move keyboard stuff
            if viewofui.frame.origin.y == viewofuipos.y {
//                print("at origin")
                let height = keyboardSize.height
                viewofui.frame.origin.y = (viewofuipos.y) - height

            }else{
                viewofui.frame.origin.y = (viewofuipos.y)
            }
        
        }
        
        
    }
    
    func hideKeyboard(_ gestureRecognizer: UIGestureRecognizer){
        let point = gestureRecognizer.location(in: viewofui)
        let indexPath = tableView.indexPathForRow(at: point)
        if indexPath != nil{
            return
        }
        messagecontent.resignFirstResponder()
        if viewofui.frame.origin.y == viewofuipos.y {
            print("at origin")
                        
        }else{
            viewofui.frame.origin.y = (viewofuipos.y)
        }

        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        managedObjectContext = appDelegate.managedObjectContext
        messagesentity = Messages(context: managedObjectContext)
//        print(messagesentity)
//        messages = messagesentity!.message
//        writing = messagesentity!.writing
        messagecontentpos = messagecontent.frame.origin
        buttonpos = button.frame.origin
        viewofuipos = viewofui.frame.origin
        loadMessages()
        tableView.delegate = self
        tableView.dataSource = self
        let applicationDocumentsDirectory: URL = {
            let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
                return paths[0]
            }()
        print(applicationDocumentsDirectory)
                    let notificationCenter = NotificationCenter.default
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: Notification.Name.UIKeyboardWillHide, object: nil)
        notificationCenter.addObserver(self, selector: #selector(adjustForKeyboard), name: Notification.Name.UIKeyboardWillChangeFrame, object: nil)
        let tap = UITapGestureRecognizer(target: self, action: #selector(hideKeyboard))
        tap.cancelsTouchesInView = false
        self.view.addGestureRecognizer(tap)
        
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

