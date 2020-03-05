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
import java.util.Arrays;
public class MergeSortAnnouncements {
    public static Announcement[] merge(Announcement[] firstList, Announcement[] secondList){
        
        
        int sizeOfFist = firstList.length;
        int sizeOfSecond = secondList.length;
        Announcement[] result = new Announcement[sizeOfFist+sizeOfSecond];
        
        int i=0;
        int j=0;
        int k=0;
        
        
        while (i<sizeOfFist && j<sizeOfSecond){
            if(firstList[i].compareTo(secondList[j])<0){
                result[k]=firstList[i];
                i++;
            }else if(firstList[i].compareTo(secondList[j])==0){
                Announcement equal = new Announcement(firstList[i].getTime(),combine(firstList[i].getContent(),secondList[j].getContent()));
                result[k]=equal;
                result=Arrays.copyOf(result, result.length-1);
                i++;
                j++;
            }else{
                result[k]=secondList[j];
                j++;
            }
            k++;
        }
        while(i<sizeOfFist){
            result[k]=firstList[i];
            i++;
            k++;
        }
        while(j<sizeOfSecond){
            result[k]=secondList[j];
            j++;
            k++;
        }
        return result;
    }
    public static Announcement[] mergeSort(Announcement[] list){
        if(list.length>1){
            int mid = list.length/2;
            Announcement[] left = new Announcement[mid];
            for(int i=0;i<left.length;i++){
                left[i]=list[i];
            }
            Announcement[] right = new Announcement[list.length-mid];
            for(int j=0;j<right.length;j++){
                right[j]=list[mid+j];
            }
            
            left=mergeSort(left);
            right=mergeSort(right);
            
            return(merge(left,right));
            
        }else{
            return list;
        }
    }
    public static String[] combine(String[] a, String[] b){
        //Concatenate two arrays
        int newLength = a.length + b.length;
        String[] result = new String[newLength];
        System.arraycopy(a,0,result,0,a.length);
        System.arraycopy(b,0,result,a.length,b.length);
        return result;
    }
}