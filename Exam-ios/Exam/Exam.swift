//
//  Exam.swift
//  Exam
//
//  Created by Apple on 2018/8/16.
//  Copyright © 2018 SJK. All rights reserved.
//

import Foundation
class Exam{
    var name: String
    var startTime: Date
    var duration: Int
    var endTime: Date {
        get{
            return startTime.addingTimeInterval(TimeInterval(duration))
        }
    }
    var readingTimeMark: Date {
        get{
            return startTime.addingTimeInterval(5*60)
        }
    }
    var thirtyMinTimeMark: Date {
        get{
            return startTime.addingTimeInterval(TimeInterval(duration-30*60))
        }
    }
    var fifteenMinTimeMark: Date {
        get{
            return startTime.addingTimeInterval(TimeInterval(duration-15*60))
        }
    }
    var fiveMinTimeMark: Date {
        get{
            return startTime.addingTimeInterval(TimeInterval(duration-15*60))
        }
    }
    
    init(name:String, startTime: Date, duration: TimeInterval) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        
        self.name = name
        self.startTime = dateFormatter.date(from: dateFormatter.string(from: startTime))!
        self.duration = Int(duration)
    }
}
