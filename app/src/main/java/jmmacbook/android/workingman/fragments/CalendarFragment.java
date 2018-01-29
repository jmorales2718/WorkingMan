package jmmacbook.android.workingman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import jmmacbook.android.workingman.HoursActivity;
import jmmacbook.android.workingman.R;
import jmmacbook.android.workingman.data.Day;
import jmmacbook.android.workingman.data.Week;
import jmmacbook.android.workingman.utils.DayCalculations;

/**
 * Created by jmmacbook on 5/19/16.
 */
public class CalendarFragment extends Fragment
{
    CalendarView calendar;

    private static CalendarFragment instance;
    private Day selectedDay;
    private OnDateClickedListener listener;

    public static CalendarFragment getInstance() {
        if(instance == null){
            instance = new CalendarFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View calendarLayout = inflater.inflate(R.layout.calendar_fragment, container, false);
        calendar = (CalendarView) calendarLayout.findViewById(R.id.calendar);
        initListener();
        if(selectedDay != null) {
            setCalendarDate(selectedDay.getDayName());
        }
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                listener.onDateClicked(DayCalculations.getDayName(year, month, dayOfMonth));
            }
        });
        return calendarLayout;
    }

    public void setCalendarDate(String date)
    {
        int indexOfComma = date.indexOf(',');
        date = date.substring(indexOfComma+2);
        int preDaySpace = date.indexOf(' ');
        int monthVal = convertMonthStringToInt(date.substring(0, preDaySpace));
        date = date.substring(preDaySpace+1);
        int postDayComma = date.indexOf(',');
        int dayVal = Integer.parseInt(date.substring(0,postDayComma));
        date = date.substring(date.indexOf(" ")+1);
        int yearVal = Integer.parseInt(date);

        long yearMillis = (yearVal - 1970)*(31556952000L);
        long monthMillis = (monthVal) * (2629746000L);
        long dayMillis = (dayVal) * (86400000L);
        long totalEpoch = yearMillis + monthMillis + dayMillis;
        calendar.setDate(totalEpoch);
    }

    public void recieveDate (Day date){
        selectedDay = date;
    }

    public static int convertMonthStringToInt(String month)
    {
        switch (month)
        {
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
        }
        return -1;
    }

    public interface OnDateClickedListener {
        public void onDateClicked(String Day);
    }


    private void initListener() {
        try {
            this.listener = (OnDateClickedListener) getActivity();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement OnDateClickedListener");
        }
    }
}
