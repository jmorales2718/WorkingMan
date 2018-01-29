package jmmacbook.android.workingman.data;

import android.content.Context;

import com.orm.SugarRecord;

import jmmacbook.android.workingman.adapter.JobsAdapter;

/**
 * Created by jmmacbook on 5/21/16.
 */
public class Day{
    private String dayName;
    private JobsAdapter jAdapter;

    public Day(Context context) {
        jAdapter = new JobsAdapter(context);
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public JobsAdapter getJAdapter() {
        return jAdapter;
    }

    public void setJAdapter(JobsAdapter jAdapter) {
        this.jAdapter = jAdapter;
    }
}
