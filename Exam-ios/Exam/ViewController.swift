//
//  ViewController.swift
//  Exam
//
//  Created by Apple on 2018/8/12.
//  Copyright © 2018 SJK. All rights reserved.
//

import UIKit
import NotificationCenter
import UserNotifications

class ViewController: UIViewController, addExamDelegate {
    

    @IBOutlet var Label: UILabel!
    @IBOutlet var examTableView: UITableView!
    
    var exams:[(Date,String)] = []
    var sortedAnnouncements:[String] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        //Format date
        let formatter = DateFormatter()
            formatter.dateFormat = "HH:mm:ss"
        
        //Set the time label
        Label.text = formatter.string(from: Date.init())
        
        //Schedule timer
        Timer.scheduledTimer(timeInterval: 1, target: self, selector: (#selector(self.updateTime)), userInfo: nil, repeats: true)
        
        //Set table view data source and delegate
        examTableView.dataSource = self
        examTableView.delegate = self
        
        //Retrive data
        print("s")
        sortedAnnouncements = UserDefaults.standard.array(forKey:"announcements") as! [String]
    }
    
    @objc func updateTime(){
        //Set the update time
        let formatter = DateFormatter()
            formatter.dateFormat = "HH:mm:ss"
        Label.text = formatter.string(from: Date.init())
    }
    
    func addExam(examName:String, beginTime: Date, duration: TimeInterval){
        
        //Format date
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        let t = dateFormatter.date(from: dateFormatter.string(from: beginTime))
        
        //Pack data into the array of announcements
        //exams.append(dateFormatter.string(from: beginTime) + "   " + examName + "  " + String(((Int(duration)/60)/60)))
        
        //Create an exam object
        let exam = Exam(name: examName, startTime: t!, duration: duration)
        
        //Append all the notifications
        exams.append((exam.startTime,exam.name+" started"))
        exams.append((exam.readingTimeMark,exam.name+" 5 minutes reading time over"))
        exams.append((exam.thirtyMinTimeMark,exam.name+" has 30 minutes remaining"))
        exams.append((exam.fifteenMinTimeMark,exam.name+" has 15 minutes remaining"))
        exams.append((exam.fiveMinTimeMark,exam.name+" has 5 minutes remaining"))
        exams.append((exam.endTime,exam.name+" has ended"))
        
        //sort the exams
        exams.sort(by: {$0.0.compare($1.0)==ComparisonResult.orderedAscending})
        
        //Refresh sorted list before adding them
        sortedAnnouncements.removeAll()
        
        for (time,name) in exams {
            let s = dateFormatter.string(from: time) + "   " + name
            sortedAnnouncements.append(s)
            if #available(iOS 10.0, *) {//Apple depreciated UILocalNotifications after iOS 10 :(
                let content = UNMutableNotificationContent()
                content.title = "Exam notification"
                content.body = s
                content.sound = UNNotificationSound.default
                let calendar = Calendar.current
                let dateComponets = calendar.dateComponents([.hour,.minute], from: time)//Create the data components for the trigger
                let trigger = UNCalendarNotificationTrigger(dateMatching: dateComponets, repeats: false)
                let request = UNNotificationRequest(identifier:name, content: content, trigger: trigger)//identifier has to be individual to prevent the notifications overwritting each other
                UNUserNotificationCenter.current().add(request, withCompletionHandler: nil)
            } else {
                // Fallback on earlier versions
            }
        }
        
        //Add the rows in the table
        /*
        examTableView.beginUpdates()
        examTableView.insertRows(at: [indexPath], with: .automatic)
        examTableView.endUpdates()*/
        
        UserDefaults.standard.set(sortedAnnouncements, forKey:"announcements")
        
        examTableView.reloadData()
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "nextView" {
            let vc2 = segue.destination as! ViewController2
            vc2.delegate = self as addExamDelegate //tell ViewController2 the delegate
        }
    }
}

//Configure the tableview
extension ViewController: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return sortedAnnouncements.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let announcements = sortedAnnouncements[indexPath.row]
        let cell = tableView.dequeueReusableCell(withIdentifier: "Exam") as! ExamCellTableViewCell
        cell.ExamCellLabel.text! = announcements
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool{
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath){
        if editingStyle == .delete{
            sortedAnnouncements.remove(at: indexPath.row)
            
            examTableView.beginUpdates()
            examTableView.deleteRows(at: [indexPath], with: .automatic)
            examTableView.endUpdates()
        }
    }
}
