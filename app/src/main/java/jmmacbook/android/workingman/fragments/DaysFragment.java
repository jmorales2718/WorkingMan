package jmmacbook.android.workingman.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import jmmacbook.android.workingman.HoursActivity;
import jmmacbook.android.workingman.R;
import jmmacbook.android.workingman.data.Day;
import jmmacbook.android.workingman.data.Job;
import jmmacbook.android.workingman.data.Week;
import jmmacbook.android.workingman.utils.DayCalculations;

/**
 * Created by jmmacbook on 5/19/16.
 */
public class DaysFragment extends Fragment {
    private static DaysFragment instance;

    static TextView tvWeeklyTotal;
    TextView tvSelectedDay;
    ImageButton ibPrevious;
    ImageButton ibNext;

    private static int[] hoursForDays;
    private static int totalMoneyMade;
    private Week currentWeek;
    private Day[] currentDays;
    private int selectedDayIndex;

    RecyclerView recyclerView;


    public static DaysFragment getInstance() {
        if(instance == null){
            instance = new DaysFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View daysLayout = inflater.inflate(R.layout.days_fragment, container, false);
        recyclerView = (RecyclerView) daysLayout.findViewById(R.id.recyclerJobs);
        recyclerView.setHasFixedSize(true);

        tvWeeklyTotal = (TextView) daysLayout.findViewById(R.id.tvWeeklyTotal);
        tvSelectedDay = (TextView) daysLayout.findViewById(R.id.tvSelectedDay);

        currentWeek = new Week(getContext());
        if (currentWeek.getDaysWeek() != null) {
            currentDays = currentWeek.getDaysWeek();
            currentWeek.setDaysWeek(currentDays);
        }

        selectedDayIndex = currentWeek.currentDay() - 1;

        tvSelectedDay.setText(currentWeek.getDay(selectedDayIndex).getDayName());
        initListener();
        initAdaptListener();
        initCalendarDateListener();
        listener.getCurrentDay(currentWeek.getDay(selectedDayIndex));
        listener.getSelectedDay(currentWeek.getDay(selectedDayIndex));
        adapterListener.getDaysWeek(currentDays);
        ((HoursActivity) getContext()).startUpdate();
        calendarDateListener.sendDate(currentDays[selectedDayIndex]);

        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(currentDays[selectedDayIndex].getJAdapter());

        ibPrevious = (ImageButton) daysLayout.findViewById(R.id.ibPrevious);
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDayIndex > 0) {
                    selectedDayIndex--;
                    tvSelectedDay.setText(currentWeek.getDay(selectedDayIndex).getDayName());
                    listener.getSelectedDay(currentWeek.getDay(selectedDayIndex));
                    recyclerView.swapAdapter(currentWeek.getDay(selectedDayIndex).getJAdapter(), true);
                    ((HoursActivity) getContext()).startUpdate();
                }

            }
        });

        ibNext = (ImageButton) daysLayout.findViewById(R.id.ibNext);
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDayIndex < Week.DAYSOFWEEK - 1) {
                    selectedDayIndex++;
                    tvSelectedDay.setText(currentWeek.getDay(selectedDayIndex).getDayName());
                    listener.getSelectedDay(currentWeek.getDay(selectedDayIndex));
                    recyclerView.swapAdapter(currentWeek.getDay(selectedDayIndex).getJAdapter(), true);
                    ((HoursActivity) getContext()).startUpdate();
                }
            }
        });

        return daysLayout;
    }

    public static void updateHoursForDays(Day[] currentDays) {
        hoursForDays = new int[7];
        for (int i = 0; i < currentDays.length; i++) {
            List<Job> daysJobs = currentDays[i].getJAdapter().getJobs();
            for (int j = 0; j < daysJobs.size(); j++) {
                hoursForDays[i] += daysJobs.get(j).getHoursWorked();
            }
        }
    }

    public static int[] getHoursForDays() {
        return hoursForDays;
    }

    public static void editDaysHours(boolean addingHours, int numHours, int indexOfDay) {
        if (addingHours) {
            hoursForDays[indexOfDay] += numHours;
        }
        else {
            hoursForDays[indexOfDay] -= numHours;
        }
    }

    public static void updateTvWeeklyTotal(Day[] days) {
        totalMoneyMade = 0;
        for (int i = 0; i < Week.DAYSOFWEEK; i++) {
            List<Job> jobs = days[i].getJAdapter().getJobs();
            for (Job job : jobs) {
                totalMoneyMade += job.getHourlyPay() * job.getHoursWorked();
            }
        }

        int indexAfter = tvWeeklyTotal.getText().toString().indexOf('$') + 1;
        tvWeeklyTotal.setText(tvWeeklyTotal.getText().toString().substring(0, indexAfter));
        tvWeeklyTotal.append(String.valueOf(totalMoneyMade));
    }

    private OnWeekCreatedListener listener;
    private OnAdapterCreatedListener adapterListener;
    private SendCalendarDateListener calendarDateListener;

    public interface OnWeekCreatedListener {
        public void getCurrentDay(Day currentDay);

        public void getSelectedDay(Day selectedDay);
    }

    public interface OnAdapterCreatedListener {
        public void getDaysWeek(Day[] days);
    }

    public interface SendCalendarDateListener {
        public void sendDate(Day selectedDay);
    }

    public void updateSelectedDay(String dayString){
        selectedDayIndex = DayCalculations.indexOfDayNameInWeek(dayString, currentDays);
        tvSelectedDay.setText(currentWeek.getDay(selectedDayIndex).getDayName());
        listener.getSelectedDay(currentWeek.getDay(selectedDayIndex));
        recyclerView.swapAdapter(currentWeek.getDay(selectedDayIndex).getJAdapter(), true);
        ((HoursActivity) getContext()).startUpdate();
    }

    private void initAdaptListener() {
        try {
            this.adapterListener = (OnAdapterCreatedListener) getActivity();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement OnAdapterCreatedListener");
        }
    }

    private void initListener() {
        try {
            this.listener = (OnWeekCreatedListener) getActivity();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement OnWeekCreatedListener");
        }
    }

    private void initCalendarDateListener() {
        try {
            this.calendarDateListener = (SendCalendarDateListener) getActivity();
        } catch (final ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement SendCalendarDateListener");
        }
    }
}
