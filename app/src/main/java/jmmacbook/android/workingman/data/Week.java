package jmmacbook.android.workingman.data;

import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;

import jmmacbook.android.workingman.utils.DayCalculations;

/**
 * Created by jmmacbook on 5/21/16.
 */

public class Week
        implements Serializable {
    private Calendar c = Calendar.getInstance();
    private Context context;

    private Day[] daysWeek;
    public static final int DAYS_OF_WEEK = 7;

    public Week(Context context) {
        this.context = context;
        daysWeek = getWeek();
    }

    /**
     * Is used to find the current week and create an array of strings that
     * represent the days of the week
     */
    private Day[] getWeek(){
        Day[] daysToReturn = new Day[DAYS_OF_WEEK];

        CalendarDate date = new CalendarDate();
        Day firstDay = getFirstDayOfWeek(date);
        daysToReturn[0] = firstDay;

        CalendarDate firstDate = new CalendarDate(firstDay);
        Day secondDay = getNextDayOfWeek(firstDate);
        daysToReturn[1] = secondDay;

        CalendarDate secondDate = new CalendarDate(secondDay);
        Day thirdDay = getNextDayOfWeek(secondDate);
        daysToReturn[2] = thirdDay;

        CalendarDate thirdDate = new CalendarDate(thirdDay);
        Day fourthDay = getNextDayOfWeek(thirdDate);
        daysToReturn[3] = fourthDay;

        CalendarDate fourthDate = new CalendarDate(fourthDay);
        Day fifthDay = getNextDayOfWeek(fourthDate);
        daysToReturn[4] = fifthDay;

        CalendarDate fifthDate = new CalendarDate(fifthDay);
        Day sixthDay = getNextDayOfWeek(fifthDate);
        daysToReturn[5] = sixthDay;

        CalendarDate sixthDate = new CalendarDate(sixthDay);
        Day seventhDay = getNextDayOfWeek(sixthDate);
        daysToReturn[6] = seventhDay;

        return daysToReturn;

    }

    private Day getNextDayOfWeek(CalendarDate prevDate){

        int nextMonth;
        int nextYear;
        int nextDay;
        int dayOfWeek;

        // New day is first of month
        if(prevDate.getDayOfMonth() == prevDate.getDaysInMonth()){
            //New day is first of year
            if(prevDate.getMonth() == 11){
                nextYear = prevDate.getYear()+1;
            }else{
                nextYear = prevDate.getYear();
            }
            nextDay = 1;
            nextMonth = prevDate.getNextMonth();
        }else{
            nextMonth = prevDate.getMonth();
            nextYear = prevDate.getYear();
            nextDay = prevDate.getDayOfMonth()+1;
        }
        if(prevDate.getDayOfWeek() == 7){
            dayOfWeek = 1;
        }else{
            dayOfWeek = prevDate.getDayOfWeek()+1;
        }
        Day day = new Day(context);
        day.setDayName(DayCalculations.getDayOfWeek(dayOfWeek) + ", " +
                DayCalculations.getMonthOfYear(nextMonth) + " " +
                DayCalculations.getDayOfMonth(nextDay) + ", " +
                DayCalculations.getYear(nextYear));
        return day;
    }

    private Day getFirstDayOfWeek(CalendarDate date){
        Day day = new Day(context);
        int daysFromFirst = date.getDayOfWeek() - Calendar.SUNDAY;

        int monthOfFirstWeekday;
        int yearOfFirstWeekday;
        int dayOfMonthForFirstWeekday = date.getDayOfMonth() - daysFromFirst; // if in current month will be the first day of the weeks date
        // case where the first day of the week is in the previous month
        if(dayOfMonthForFirstWeekday < 1){
            monthOfFirstWeekday = DayCalculations.getPreviousMonth(date.getMonth());
            // case where first day of week is in previous year
            if(monthOfFirstWeekday == 11){
                yearOfFirstWeekday = date.getYear()-1;
            }else{
                yearOfFirstWeekday = date.getYear();
            }
            dayOfMonthForFirstWeekday = date.getDaysInPreviousMonth() + dayOfMonthForFirstWeekday;
        // first day of the week must be in current month
        }else{
            monthOfFirstWeekday = date.getMonth();
            yearOfFirstWeekday = date.getYear();
        }
        String dayName = DayCalculations.getDayOfWeek(Calendar.SUNDAY) + ", " +
                DayCalculations.getMonthOfYear(monthOfFirstWeekday) + " " +
                DayCalculations.getDayOfMonth(dayOfMonthForFirstWeekday) + ", " +
                DayCalculations.getYear(yearOfFirstWeekday);
        day.setDayName(dayName);
        return day;
    }


//    private Day[] getWeek() {
//        Day[] daysToReturn = new Day[DAYS_OF_WEEK];
//
//        int currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//        int currentMonth = c.get(Calendar.MONTH);
//        int previousMonth = getPreviousMonth(currentMonth);
//        int nextMonth = getNextMonth(currentMonth);
//        int daysInCurrent = getNumDaysInMonth(currentMonth);
//        int daysInPrevious = getNumDaysInMonth(previousMonth);
//        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//        int currentYear = c.get(Calendar.YEAR);
//        int posDif = Calendar.SATURDAY - currentDayOfWeek;
//        int negDif = currentDayOfWeek;
//
//        String day = "";
//        Day dayForWeek;
//        int i;
//        for (i = 0; i < posDif; i++) {
//            dayForWeek = new Day(context);
//            int tempDay = 0;
//            int tempDOM = 0;
//            int tempMonth = 0;
//            int nextDays = dayOfMonth + (posDif - i);
//            int nextDOW = DAYS_OF_WEEK - i;
//
//            if (nextDays > daysInCurrent) {
//                int daysOver = nextDays - daysInCurrent;
//                tempDOM = daysOver;
//                tempMonth = nextMonth;
//            }
//            else {
//                tempDOM = nextDays;
//                tempMonth = currentMonth;
//            }
//
//            tempDay = nextDOW;
//            day = getDayOfWeek(tempDay) + ", " + getMonthOfYear(tempMonth) + " " + getDayOfMonth(tempDOM) + ", " + getYear(currentYear);
//            dayForWeek.setDayName(day);
//            daysToReturn[currentDayOfWeek + (posDif - (i + 1))] = dayForWeek;
//            dayForWeek.setJAdapter(new JobsAdapter(context));
//            System.out.println(day);
//
//        }
//        for (i = 0; i < negDif + 1; i++) {
//            dayForWeek = new Day(context);
//            int tempDay = 0;
//            int tempDOM = 0;
//            int tempMonth = 0;
//            int prevDays = dayOfMonth - (negDif - i) + 1;
//            int prevDOW = currentDayOfWeek - (negDif - i) + 1;
//            if (prevDays < 1) {
//                tempDOM = daysInPrevious - prevDays;
//                tempMonth = previousMonth;
//            }
//            else {
//                tempDOM = prevDays;
//                tempMonth = currentMonth;
//            }
//            tempDay = prevDOW;
//            day = getDayOfWeek(tempDay) + ", " + getMonthOfYear(tempMonth) + " " + getDayOfMonth(tempDOM) + ", " + getYear(currentYear);
//            dayForWeek.setDayName(day);
//            daysToReturn[currentDayOfWeek - ((negDif - i))] = dayForWeek;
//            dayForWeek.setJAdapter(new JobsAdapter(context));
//        }
//
//        return daysToReturn;
//    }

    public Day getDay(int index) {
        return daysWeek[index];
    }

    public int currentDayOfWeek() {
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public Day[] getDaysWeek() {
        return daysWeek;
    }

    public void setDaysWeek(Day[] daysWeek) {
        this.daysWeek = daysWeek;
    }
}
