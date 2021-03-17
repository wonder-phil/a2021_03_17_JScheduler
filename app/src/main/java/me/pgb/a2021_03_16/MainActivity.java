package me.pgb.a2021_03_16;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "SCHED_";
    private final int JOB_ID = 109;
    private Button cancelButton;
    private Button scheduleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelButton = findViewById(R.id.cancel_button);
        scheduleButton = findViewById(R.id.schedule_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJob();
            }
        });

        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });


    }

   public void scheduleJob() {
       ComponentName componentName = new ComponentName(this, MyJobService.class);
       JobInfo info = new JobInfo.Builder(JOB_ID, componentName)
               .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
               .setPersisted(true)
               .setPeriodic(15* 60*1000)
               .build();

       JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
       int resultCode = scheduler.schedule(info);
       if (resultCode == JobScheduler.RESULT_SUCCESS) {
           Log.i(TAG,"job scheduled");
       } else {
           Log.i(TAG,"job schedule FAILED");
       }
   }

   public void cancelJob() {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(JOB_ID);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}