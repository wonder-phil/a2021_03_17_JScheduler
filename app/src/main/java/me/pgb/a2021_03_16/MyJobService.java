package me.pgb.a2021_03_16;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import javax.security.auth.login.LoginException;

public class MyJobService extends JobService {

    private static final String TAG = "SCHED_";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i(TAG, "onStartJob ");
        doBackgroundwork(params);
        return true; // true for long running
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob: ");
        jobCancelled = true;
        return true; // true retry later if stopped
    }

    private void doBackgroundwork(JobParameters params) {
        Thread myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "entered run ");
                for (int i = 0; i < 5; i++) {
                    if (jobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "run: " + String.valueOf(i).toString());
                }
                jobFinished(params, true);
            }
        });
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
