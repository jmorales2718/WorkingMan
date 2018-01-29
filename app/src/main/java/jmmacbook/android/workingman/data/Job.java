package jmmacbook.android.workingman.data;

import com.orm.SugarRecord;

import java.io.Serializable;

import jmmacbook.android.workingman.adapter.JobsAdapter;

/**
 * Created by jmmacbook on 5/19/16.
 */
public class Job
        extends SugarRecord
        implements Serializable {
    private String jobName;
    private int hoursWorked;
    private int hourlyPay;
    private String dayOfJob;
    private JobsAdapter jobsAdapter;

    public Job(JobsAdapter jobsAdapter) {
        this.jobsAdapter = jobsAdapter;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public int getHourlyPay() {
        return hourlyPay;
    }

    public void setHourlyPay(int hourlyPay) {
        this.hourlyPay = hourlyPay;
    }

    public void setDayOfJob(String dayOfJob) {
        this.dayOfJob = dayOfJob;
    }
}
