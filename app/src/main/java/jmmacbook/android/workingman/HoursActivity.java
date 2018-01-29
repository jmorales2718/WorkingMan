package jmmacbook.android.workingman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.astuetz.PagerSlidingTabStrip;

import jmmacbook.android.workingman.data.Day;
import jmmacbook.android.workingman.data.Job;
import jmmacbook.android.workingman.fragments.CalendarFragment;
import jmmacbook.android.workingman.fragments.DaysFragment;
import jmmacbook.android.workingman.utils.DayCalculations;

public class HoursActivity
        extends AppCompatActivity
        implements DaysFragment.OnWeekCreatedListener,
        DaysFragment.OnAdapterCreatedListener,
        DaysFragment.SendCalendarDateListener,
        CalendarFragment.OnDateClickedListener
{
    public static String CALENDAR_DATE_STRING = "CALENDAR_DATE_STRING";

    private HoursPagerAdapter hoursPagerAdapter;
    private ViewPager viewPager;


    EditText etNewJobName;
    EditText etNewHoursWorked;
    EditText etNewHourlyRate;

    String newJobName;
    String newHoursWorked;
    String newHourlyRate;

    private Day[] currentDays;
    private Day currentDay;
    private Day selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));

        hoursPagerAdapter =
                new HoursPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.vPager);
        viewPager.setAdapter(hoursPagerAdapter);

        PagerSlidingTabStrip tabStrip =
                (PagerSlidingTabStrip) findViewById(R.id.pagerSlidingTabs);
        tabStrip.setViewPager(viewPager);
        tabStrip.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        tabStrip.setIndicatorColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.mbAddJob)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final LayoutInflater inflater = getLayoutInflater();
            final View layoutView = inflater.inflate(R.layout.dialog_layout, null);

            etNewJobName = (EditText) layoutView.findViewById(R.id.etJobName);
            etNewHoursWorked = (EditText) layoutView.findViewById(R.id.etHoursWorked);
            etNewHourlyRate = (EditText) layoutView.findViewById(R.id.etDollarsPerHour);
            builder
                    .setView(layoutView)
                    .setTitle("Add New Job")
                    .setIcon(android.R.drawable.ic_menu_add)
                    .setPositiveButton("Add Job", null)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();

            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!etNewJobName.getText().toString().equals("") &&
                            !etNewHoursWorked.getText().toString().equals("") &&
                            !etNewHourlyRate.getText().toString().equals("") &&
                            DaysFragment.getHoursForDays()[DayCalculations.indexOfDayInWeek(selectedDay, currentDays)] +
                            Short.parseShort(etNewHoursWorked.getText().toString())
                            <= 24)
                    {
                        newJobName = etNewJobName.getText().toString();
                        newHoursWorked = etNewHoursWorked.getText().toString();
                        newHourlyRate = etNewHourlyRate.getText().toString();
                        Job j = new Job(selectedDay.getJAdapter());
                        j.setJobName(newJobName);
                        j.setHoursWorked(Integer.parseInt(newHoursWorked));
                        j.setHourlyPay(Integer.parseInt(newHourlyRate));
                        j.setDayOfJob(selectedDay.getDayName());
                        selectedDay.getJAdapter().addJob(j);
                        selectedDay.getJAdapter().notifyDataSetChanged();
                        DaysFragment.updateTvWeeklyTotal(selectedDay.getJAdapter().getDays());
                        alertDialog.dismiss();
                    }else if(etNewJobName.getText().toString().equals("") &&
                            etNewHoursWorked.getText().toString().equals("") &&
                            etNewHourlyRate.getText().toString().equals(""))
                    {
                        etNewJobName.setError("Must enter a name");
                        etNewHoursWorked.setError("Must enter a number of hours worked");
                        etNewHourlyRate.setError("Must enter an hourly rate");
                    }else if(etNewJobName.getText().toString().equals("") &&
                            etNewHoursWorked.getText().toString().equals(""))
                    {
                        etNewJobName.setError("Must enter a name");
                        etNewHoursWorked.setError("Must enter a number of hours worked");
                    }else if(etNewJobName.getText().toString().equals("") &&
                            etNewHourlyRate.getText().toString().equals(""))
                    {
                        etNewJobName.setError("Must enter a name");
                        etNewHourlyRate.setError("Must enter an hourly rate");
                    }else if(etNewHoursWorked.getText().toString().equals("") &&
                            etNewHourlyRate.getText().toString().equals(""))
                    {
                        etNewHoursWorked.setError("Must enter a number of hours worked");
                        etNewHourlyRate.setError("Must enter an hourly rate");
                    }else if(etNewJobName.getText().toString().equals(""))
                    {
                        etNewJobName.setError("Must enter a name");
                    }else if(etNewHoursWorked.getText().toString().equals(""))
                    {
                        etNewHoursWorked.setError("Must enter a number of hours worked");
                    }else if(etNewHourlyRate.getText().toString().equals(""))
                    {
                        etNewHourlyRate.setError("Must enter an hourly rate");
                    }else if(DaysFragment.getHoursForDays()[DayCalculations.indexOfDayInWeek(selectedDay, currentDays)] +
                            Short.parseShort(etNewHoursWorked.getText().toString())
                            > 24)
                    {
                        etNewHoursWorked.setError("Cannot work more than 24 hours per day");
                    }

                }
                });
            return true;
        }
        else if(id == R.id.mbDeleteAll)
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .setTitle("Delete All Jobs")
                    .setMessage("Are you sure you want to delete all jobs?")
                    .setPositiveButton("Delete All", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            for (int i = 0; i <currentDays.length; i++)
                            {
                                currentDays[i].getJAdapter().removeAll();
                            }
                            DaysFragment.updateTvWeeklyTotal(currentDays);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    })
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getCurrentDay(Day currentDay)
    {
        this.currentDay = currentDay;
    }

    @Override
    public void getSelectedDay(Day selectedDay)
    {
        this.selectedDay = selectedDay;
        selectedDay.getJAdapter().setSelectedDay(selectedDay);
    }

    @Override
    public void getDaysWeek(Day[] days) {
        currentDays = days;
        selectedDay.getJAdapter().setDays(days);
    }

    public void startUpdate()
    {
        selectedDay.getJAdapter().updateFields(this,selectedDay,currentDays);
        DaysFragment.updateTvWeeklyTotal(currentDays);
        DaysFragment.updateHoursForDays(currentDays);

    }

    @Override
    public void sendDate(Day selectedDay) {
        CalendarFragment calendarFragment = (CalendarFragment)hoursPagerAdapter.getItem(1);
        calendarFragment.recieveDate(selectedDay);
    }

    @Override
    public void onDateClicked(String day) {
        DaysFragment daysFragment = (DaysFragment)hoursPagerAdapter.getItem(0);
        daysFragment.updateSelectedDay(day);
    }

    public class HoursPagerAdapter extends FragmentPagerAdapter
    {
        SparseArray<Fragment> registeredFragments = new SparseArray<>();

        public HoursPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Fragment newFragment = null;
            switch (position)
            {
                case 0:
                    newFragment = DaysFragment.getInstance();
                    break;
                case 1:
                    newFragment = CalendarFragment.getInstance();
                    break;
            }
            registeredFragments.put(position, newFragment);
            return newFragment;
        }

        @Override
        public int getCount()
        {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0:
                    return "Day";
                case 1:
                    return "Calendar";
            }
            return null;
        }
    }
}
