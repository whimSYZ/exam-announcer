//
//  ViewController2.swift
//  Exam
//
//  Created by Apple on 2018/8/13.
//  Copyright © 2018 SJK. All rights reserved.
//

import UIKit

protocol addExamDelegate: class {
    func addExam(examName:String, beginTime: Date, duration: TimeInterval)
}//Add the protocol

class ViewController2: UIViewController {
    
    weak var delegate: addExamDelegate?

    @IBOutlet var doneButton: UIBarButtonItem!
    @IBOutlet var cancelButton: UIBarButtonItem!
    @IBOutlet var examTextField: UITextField!
    @IBOutlet var examTimePicker: UIDatePicker!
    @IBOutlet var durationTimePicker: UIDatePicker!
    

    @IBAction func cancelView(_ sender: Any) {
        dismiss(animated: true, completion: nil)//Simply cancel the window
    }
    
    @IBAction func doneView(_ sender: Any) {
       
        //can't direclt modify the tableView here as it is against the MVC paradigm. Creating a new object instance with ViewController() does not hook up any outlets such as the tableView. The correct way is to create a delegate and protocol
        
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        
        var beginTime = dateFormatter.date(from: dateFormatter.string(from: examTimePicker.date))
        beginTime = examTimePicker.date
        
        if examTextField.text != "" {
            delegate?.addExam(examName: examTextField.text!, beginTime: beginTime!, duration: durationTimePicker.countDownDuration)//use delegate to transfer the data
        }
        dismiss(animated: true, completion: nil)
        //performSegue(withIdentifier: "goBack", sender: self)
    }
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}
