package jmmacbook.android.workingman.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import jmmacbook.android.workingman.data.Day;

/**
 * Created by jmmacbook on 1/29/18.
 */

public class DayCalculations {

    public static Calendar c = Calendar.getInstance();

    public static int getDayOfWeekFromDay(Day day){
        String dayName = day.getDayName();
        dayName = dayName.substring(0, dayName.indexOf(','));
        return getDayOfWeek(dayName);
    }

    public static int getDayOfMonthFromDay(Day day){
        String dayName = day.getDayName();
        dayName = dayName.substring(dayName.indexOf(' ')+1);
        dayName = dayName.substring(dayName.indexOf(' ')+1);
        dayName = dayName.substring(0, dayName.indexOf(','));
        return Integer.parseInt(dayName);
    }

    public static int getMonthFromDay(Day day){
        String dayName = day.getDayName();
        dayName = dayName.substring(dayName.indexOf(' ')+1);
        dayName = dayName.substring(0, dayName.indexOf(' '));
        return getMonthOfYear(dayName);
    }

    public static int getYearFromDay(Day day){
        String dayName = day.getDayName();
        dayName = dayName.substring(dayName.lastIndexOf(' ')+1);
        return Integer.parseInt(dayName);
    }

    public static int getDayOfWeek(String dayOfWeek){
        switch (dayOfWeek){
            case "Sunday":
                return 1;
            case "Monday":
                return 2;
            case "Tuesday":
                return 3;
            case "Wednesday":
                return 4;
            case "Thursday":
                return 5;
            case "Friday":
                return 6;
            case "Saturday":
                return 7;
            default:
                return -1;
        }
    }

    public static int getMonthOfYear(String month){
        switch (month){
            case "January":
                return 0;
            case "February":
                return 1;
            case "March":
                return 2;
            case "April":
                return 3;
            case "May":
                return 4;
            case "June":
                return 5;
            case "July":
                return 6;
            case "August":
                return 7;
            case "September":
                return 8;
            case "October":
                return 9;
            case "November":
                return 10;
            case "December":
                return 11;
            default:
                return -1;

        }
    }

    public static int indexOfDayInWeek(Day day, Day[] currentDays) {
        for (int i = 0; i < currentDays.length; i++) {
            if (day == currentDays[i]) {
                return i;
            }
        }
        return -1;
    }

    public static int indexOfDayNameInWeek(String dayName, Day[] currentDays) {
        for (int i = 0; i < currentDays.length; i++) {
            if (dayName.equals(currentDays[i].getDayName())) {
                return i;
            }
        }
        return -1;
    }

    public static String getDayName(int year, int month, int dayOfMonth) {
        String dayOfWeek = "";
        String dateString = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
        try {
            Date date = new SimpleDateFormat("yyyy-M-d").parse(dateString);
            dayOfWeek = new SimpleDateFormat("EEEE, MMMM d, YYYY", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayOfWeek;
    }

    public static String getDayOfWeek(int dOWInt) {
        String dayOfWeek;
        int dOW = dOWInt;
        if (dOW == 1) {
            dayOfWeek = "Sunday";
        }
        else if (dOW == 2) {
            dayOfWeek = "Monday";
        }
        else if (dOW == 3) {
            dayOfWeek = "Tuesday";
        }
        else if (dOW == 4) {
            dayOfWeek = "Wednesday";
        }
        else if (dOW == 5) {
            dayOfWeek = "Thursday";
        }
        else if (dOW == 6) {
            dayOfWeek = "Friday";
        }
        else {
            dayOfWeek = "Saturday";
        }
        return dayOfWeek;
    }

    public static String getMonthOfYear(int mOYInt) {
        String monthOfYear = null;
        int mOY = mOYInt;
        switch (mOY) {
            case 0:
                monthOfYear = "January";
                break;
            case 1:
                monthOfYear = "February";
                break;
            case 2:
                monthOfYear = "March";
                break;
            case 3:
                monthOfYear = "April";
                break;
            case 4:
                monthOfYear = "May";
                break;
            case 5:
                monthOfYear = "June";
                break;
            case 6:
                monthOfYear = "July";
                break;
            case 7:
                monthOfYear = "August";
                break;
            case 8:
                monthOfYear = "September";
                break;
            case 9:
                monthOfYear = "October";
                break;
            case 10:
                monthOfYear = "November";
                break;
            case 11:
                monthOfYear = "December";
                break;
        }
        return monthOfYear;
    }

    public static String getYear(int yInt) {
        String year;
        int y = yInt;
        year = String.valueOf(y);
        return year;
    }

    public static String getDayOfMonth(int dOMInt) {

        String dayOfMonth;
        int dOM = dOMInt;
        dayOfMonth = String.valueOf(dOM);
        return dayOfMonth;
    }

    public static int getNumDaysInMonth(int month, int year, int dayOfMonth) {
        Calendar temp;
        int maxDaysInMonth = -1;
        temp = new GregorianCalendar(year, month, dayOfMonth);
        maxDaysInMonth = temp.getActualMaximum(temp.DAY_OF_MONTH);
        return maxDaysInMonth;
    }

    public static int getNextMonth(int currentMonth) {
        if (currentMonth == 12) {
            return 0;
        }
        else {
            return currentMonth + 1;
        }
    }

    public static int getPreviousMonth(int currentMonth) {
        if (currentMonth == 0) {
            return 11;
        }
        else {
            return currentMonth - 1;
        }
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
