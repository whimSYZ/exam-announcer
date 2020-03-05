/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exam;

/**
 *
 * @author Brandon Shi
 */
/*
    This class is one of the fundamental data structure representing the individual exams.
*/
import java.time.LocalTime;
public class Exam {
    private String name;
    private int duration;
    private LocalTime startTime;
    private LocalTime endTime;
    private double accommodation;
    
    Exam(String name, int duration, LocalTime startTime, double accommodation){
        this.name=name;
        this.duration=(int)(duration*(1+accommodation));//The accomodation in the parameter is a ratio like 0.25 or 0.5. Therefore to calculate the actual exam time, the accomodation has to be added to 1 and multiplied by the exam lemgth
        this.startTime=startTime;
        this.endTime=startTime.plusMinutes(this.duration);//The duration in the parameter is a value of minutes. Note it is the duration that has been changed.
        this.accommodation = accommodation;
    }
    
    public String getName(){
        return name;//return the name
    }
    public LocalTime startTime(){
        return startTime;//return the reading time mark
    }
    public LocalTime readingTime(){
        return startTime.plusMinutes(5);//return the reading time mark
    }
    public LocalTime thirtyMinutes(){
        return endTime.minusMinutes(30);//return the 30 minutes left time mark
    }
    public LocalTime fifteenMinutes(){
        return endTime.minusMinutes(15);//return the 15 minutes time mark
    }
    public LocalTime fiveMinutes(){
        return endTime.minusMinutes(5);//return the 5 minutes time mark
    }
    public LocalTime endTime(){
        return endTime;//return the end of exam time mark
    }
}
