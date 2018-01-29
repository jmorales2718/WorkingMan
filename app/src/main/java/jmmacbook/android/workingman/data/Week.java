package jmmacbook.android.workingman.data;

import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import jmmacbook.android.workingman.adapter.JobsAdapter;

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

    private Day[] getWeek() {
        Day[] daysToReturn = new Day[DAYS_OF_WEEK];

        int currentDay = c.get(Calendar.DAY_OF_WEEK);
        int currentMonth = c.get(Calendar.MONTH);
        int previousMonth = getPreviousMonth(currentMonth);
        int nextMonth = getNextMonth(currentMonth);
        int daysInCurrent = getNumDaysInMonth(currentMonth);
        int daysInPrevious = getNumDaysInMonth(previousMonth);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int currentYear = c.get(Calendar.YEAR);
        int posDif = Calendar.SATURDAY - currentDay;
        int negDif = currentDay;

        String day = "";
        Day dayForWeek;
        int i;
        for (i = 0; i < posDif; i++) {
            dayForWeek = new Day(context);
            int tempDay = 0;
            int tempDOM = 0;
            int tempMonth = 0;
            int nextDays = dayOfMonth + (posDif - i);
            int nextDOW = DAYS_OF_WEEK - i;

            if (nextDays > daysInCurrent) {
                int daysOver = nextDays - daysInCurrent;
                tempDOM = daysOver;
                tempMonth = nextMonth;
            }
            else {
                tempDOM = nextDays;
                tempMonth = currentMonth;
            }

            tempDay = nextDOW;
            day = getDayOfWeek(tempDay) + ", " + getMonthOfYear(tempMonth) + " " + getDayOfMonth(tempDOM) + ", " + getYear(currentYear);
            dayForWeek.setDayName(day);
            daysToReturn[currentDay + (posDif - (i + 1))] = dayForWeek;
            dayForWeek.setJAdapter(new JobsAdapter(context));
            System.out.println(day);

        }
        for (i = 0; i < negDif + 1; i++) {
            dayForWeek = new Day(context);
            int tempDay = 0;
            int tempDOM = 0;
            int tempMonth = 0;
            int prevDays = dayOfMonth - (negDif - i) + 1;
            int prevDOW = currentDay - (negDif - i) + 1;
            if (prevDays < 1) {
                tempDOM = daysInPrevious - prevDays;
                tempMonth = previousMonth;
            }
            else {
                tempDOM = prevDays;
                tempMonth = currentMonth;
            }
            tempDay = prevDOW;
            day = getDayOfWeek(tempDay) + ", " + getMonthOfYear(tempMonth) + " " + getDayOfMonth(tempDOM) + ", " + getYear(currentYear);
            dayForWeek.setDayName(day);
            daysToReturn[currentDay - ((negDif - i))] = dayForWeek;
            dayForWeek.setJAdapter(new JobsAdapter(context));
        }

        return daysToReturn;
    }

    private int getPreviousMonth(int currentMonth) {
        if (currentMonth == 0) {
            return 11;
        }
        else {
            return currentMonth - 1;
        }
    }

    private int getNextMonth(int currentMonth) {
        if (currentMonth == 12) {
            return 0;
        }
        else {
            return currentMonth + 1;
        }
    }

    private String getDayOfMonth(int dOMInt) {

        String dayOfMonth;
        int dOM = dOMInt;
        dayOfMonth = String.valueOf(dOM);
        return dayOfMonth;
    }

    private String getDayOfWeek(int dOWInt) {
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

    private String getMonthOfYear(int mOYInt) {
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

    private String getYear(int yInt) {
        String year;
        int y = yInt;
        year = String.valueOf(y);
        return year;
    }

    public int getNumDaysInMonth(int month) {
        int currentYear = 0;
        int currentDay = 0;
        Calendar temp;
        int monthOfYear = -1;
        int mOY = month;
        switch (mOY) {
            case 0:
                temp = new GregorianCalendar(currentYear, Calendar.JANUARY, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 1:
                temp = new GregorianCalendar(currentYear, Calendar.FEBRUARY, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 2:
                temp = new GregorianCalendar(currentYear, Calendar.MARCH, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 3:
                temp = new GregorianCalendar(currentYear, Calendar.APRIL, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 4:
                temp = new GregorianCalendar(currentYear, Calendar.MAY, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 5:
                temp = new GregorianCalendar(currentYear, Calendar.JUNE, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 6:
                temp = new GregorianCalendar(currentYear, Calendar.JULY, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 7:
                temp = new GregorianCalendar(currentYear, Calendar.AUGUST, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 8:
                temp = new GregorianCalendar(currentYear, Calendar.SEPTEMBER, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 9:
                temp = new GregorianCalendar(currentYear, Calendar.OCTOBER, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 10:
                temp = new GregorianCalendar(currentYear, Calendar.NOVEMBER, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
            case 11:
                temp = new GregorianCalendar(currentYear, Calendar.DECEMBER, currentDay);
                monthOfYear = c.getActualMaximum(temp.DAY_OF_MONTH);
                break;
        }
        return monthOfYear;
    }

    public Day getDay(int index) {
        return daysWeek[index];
    }

    public int currentDay() {
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public Day[] getDaysWeek() {
        return daysWeek;
    }

    public void setDaysWeek(Day[] daysWeek) {
        this.daysWeek = daysWeek;
    }
}
