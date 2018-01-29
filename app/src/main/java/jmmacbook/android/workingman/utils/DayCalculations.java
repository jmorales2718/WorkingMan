package jmmacbook.android.workingman.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jmmacbook.android.workingman.data.Day;

/**
 * Created by jmmacbook on 1/29/18.
 */

public class DayCalculations {

    public static int indexOfDayInWeek(Day day, Day[] currentDays){
        for (int i = 0; i < currentDays.length; i++) {
            if(day == currentDays[i]){
                return i;
            }
        }
        return -1;
    }

    public static int indexOfDayNameInWeek(String dayName, Day[] currentDays){
        for(int i = 0; i < currentDays.length; i++){
            if(dayName.equals(currentDays[i].getDayName())){
                return i;
            }
        }
        return -1;
    }

    public static String getDayName(int year, int month, int dayOfMonth){
        String dayOfWeek = "";
        String dateString = String.format("%d-%d-%d", year, month+1, dayOfMonth);
        try{
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            dayOfWeek = new SimpleDateFormat("EEEE, MMMM d, YYYY", Locale.ENGLISH).format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }

        return dayOfWeek;
    }

//    public static String convertMonthIndexToMonth(int indexOfMonth){
//        switch (indexOfMonth){
//            case 0:
//                return "January";
//            case 1:
//                return "February";
//            case 2:
//                return "March";
//            case 3:
//                return "April";
//            case 4:
//                return "May";
//            case 5:
//                return "June";
//            case 6:
//                return "July";
//            case 7:
//                return "August";
//            case 8:
//                return "September";
//            case 9:
//                return "October";
//            case 10:
//                return "November";
//            case 11:
//                return "December";
//            default:
//                return null;
//        }
//    }
}
