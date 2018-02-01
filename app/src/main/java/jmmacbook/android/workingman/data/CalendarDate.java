package jmmacbook.android.workingman.data;

import java.util.Calendar;

import jmmacbook.android.workingman.HoursActivity;
import jmmacbook.android.workingman.utils.DayCalculations;

/**
 * Created by jmmacbook on 1/31/18.
 */

public class CalendarDate {

    private int dayOfWeek;
    private int dayOfMonth;
    private int month;
    private int year;
    private int daysInMonth;
    private int nextMonth;
    private int daysInPreviousMonth;


    public CalendarDate(){
        Calendar c = Calendar.getInstance();
        setDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
        setDayOfMonth(c.get(Calendar.DAY_OF_MONTH));
        setMonth(c.get(Calendar.MONTH));
        setYear(c.get(Calendar.YEAR));
        setDaysInMonth(DayCalculations.getNumDaysInMonth(c.get(Calendar.MONTH)));
        setNextMonth(DayCalculations.getNextMonth(c.get(Calendar.MONTH)));
        setDaysInPreviousMonth(DayCalculations.getNumDaysInMonth(DayCalculations.getPreviousMonth(c.get(Calendar.MONTH))));
    };

    public CalendarDate(Day day){
        Calendar c = Calendar.getInstance();
        setDayOfWeek(DayCalculations.getDayOfWeekFromDay(day));
        setDayOfMonth(DayCalculations.getDayOfMonthFromDay(day));
        setMonth(DayCalculations.getMonthFromDay(day));
        setYear(DayCalculations.getYearFromDay(day));
        setDaysInMonth(DayCalculations.getNumDaysInMonth(getMonth()));
        setNextMonth(DayCalculations.getNextMonth(getMonth()));
        setDaysInPreviousMonth(DayCalculations.getNumDaysInMonth(
                DayCalculations.getPreviousMonth(getMonth())));

    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDaysInMonth() {
        return daysInMonth;
    }

    public void setDaysInMonth(int daysInMonth) {
        this.daysInMonth = daysInMonth;
    }

    public int getNextMonth() {
        return nextMonth;
    }

    public void setNextMonth(int nextMonth) {
        this.nextMonth = nextMonth;
    }

    public int getDaysInPreviousMonth() {
        return daysInPreviousMonth;
    }

    public void setDaysInPreviousMonth(int daysInPreviousMonth) {
        this.daysInPreviousMonth = daysInPreviousMonth;
    }

//    public Day getDay(){
//        //Todo: return day
//    }
}
