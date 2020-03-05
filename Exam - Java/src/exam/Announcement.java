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
    This is one of the fundamental data structure. Each announcement contains two basic information - Time and Content
*/
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Announcement implements Comparable <Announcement>, Serializable{
    private String[] content;
    private LocalTime time;
    private boolean displayed;
    Announcement(LocalTime time, String[] content){
        this.content = content;
        this.time = time;
        this.displayed = false;
    }
    public LocalTime getTime(){
        return time;
    }
    public String[] getContent(){
        return content;
    }
    public boolean isDisplayed(){
        return displayed;
    }
    public void display(){
        displayed = true;
    }
    
    //Overriden methods
    @Override
    public int compareTo(Announcement a){
        DateTimeFormatter minFormat = DateTimeFormatter.ofPattern("HH:mm");
        if(time.format(minFormat).equals(a.getTime().format(minFormat))){
            return 0;
        }
        else{
            return time.compareTo(a.getTime());
        }
    }//The compareTo method is overriden in the Collection class, specify how to compare two announcement objects - compare the time. When Collection.sort() is implemented, the compareTo() method is used to compare Announcement objects
    @Override
    public String toString(){
        DateTimeFormatter minFormat = DateTimeFormatter.ofPattern("HH:mm");
        String result = time.format(minFormat)+"    "+content[0]+"\n";
        for(int i=1;i<content.length;i++){
            result+="         "+content[i]+"\n";
        }
        result += "\n";
        return result;
    }//For checking purposes, one can just System.out.println(Announcement)
}