package jmmacbook.android.workingman.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jmmacbook.android.workingman.R;
import jmmacbook.android.workingman.data.Day;
import jmmacbook.android.workingman.data.Job;
import jmmacbook.android.workingman.fragments.DaysFragment;
import jmmacbook.android.workingman.utils.DayCalculations;


/**
 * Created by jmmacbook on 5/19/16.
 */
public class JobsAdapter
        extends RecyclerView.Adapter<JobsAdapter.ViewHolder>
        implements Serializable {
    private Context context;
    private List<Job> jobs = new ArrayList<>();


    private Day[] days;
    private Day selectedDay;

    public JobsAdapter(Context context) {
        this.context = context;
        jobs.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_row, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvJobName.setText(jobs.get(position).getJobName());
        holder.tvHoursWorked.setText(String.valueOf(jobs.get(position).getHoursWorked()));
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeJob(position);
                notifyDataSetChanged();
                DaysFragment.updateTvWeeklyTotal(getDays());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobs.size();
    }

    public void addJob(Job job) {
        int finalIndex = jobs.size();
        job.save();
        jobs.add(finalIndex, job);
        notifyItemChanged(finalIndex);
        DaysFragment.editDaysHours(true, job.getHoursWorked(),
                DayCalculations.indexOfDayInWeek(selectedDay, days));
    }

    public void removeJob(int position) {
        DaysFragment.editDaysHours(false, jobs.get(position).getHoursWorked(),
                DayCalculations.indexOfDayInWeek(selectedDay, days));
        jobs.get(position).delete();
        jobs.remove(position);
    }

    public void removeAll() {
        for (int i = jobs.size() - 1; i >= 0; i--) {
            removeJob(i);
        }
        notifyDataSetChanged();
    }

    public void loadJobs(String dayName) {
        jobs = Job.find(Job.class, "day_of_job= ?", dayName);
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public Day[] getDays() {
        return days;
    }

    public void setDays(Day[] days) {
        this.days = days;
    }

    public Day getSelectedDay() {
        return selectedDay;
    }

    public void setSelectedDay(Day selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void updateFields(Context context, Day selectedDay, Day[] days) {
        setSelectedDay(selectedDay);
        setDays(days);
        loadJobs(selectedDay.getDayName());
    }


    public static class ViewHolder
            extends RecyclerView.ViewHolder
            implements Serializable

    {
        private TextView tvJobName;
        private TextView tvHoursWorked;
        private ImageButton ibDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvJobName = (TextView) itemView.findViewById(R.id.tvJobName);
            tvHoursWorked = (TextView) itemView.findViewById(R.id.tvHoursWorked);
            ibDelete = (ImageButton) itemView.findViewById(R.id.ibDelete);
        }
    }

}
