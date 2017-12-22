package net.kiriti.peelabus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Alerts extends AppCompatActivity {

    private static final String Job_Tag = "my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;
    TextView pickup;
    TextView drop;
    Button mins_20_pick;
    Button mins_15_pick;
    Button mins_05_pick;
    SharedPreferences sharedPreferencesalert;
    public static String alertTimeselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        mins_20_pick = (Button) findViewById(R.id.mins_20_pickup_alert);
        mins_15_pick = (Button) findViewById(R.id.mins_15_pickup_alert);
        mins_05_pick = (Button) findViewById(R.id.mins_5_pickup_alert);

        mins_15_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.parseColor("#007B00"));
                mins_20_pick.setBackgroundColor(Color.WHITE);
                mins_05_pick.setBackgroundColor(Color.WHITE);
                String time = "15 mins";
                storeTime(time);
                stopJob();

            }
        });

        mins_20_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackgroundColor(Color.parseColor("#007B00"));
                mins_15_pick.setBackgroundColor(Color.WHITE);
                mins_05_pick.setBackgroundColor(Color.WHITE);
                String time = "20 mins";
                storeTime(time);
            }
        });

        mins_05_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setBackgroundColor(Color.parseColor("#007B00"));
                mins_15_pick.setBackgroundColor(Color.WHITE);
                mins_20_pick.setBackgroundColor(Color.WHITE);
                String time = "05 mins";
                storeTime(time);
                startJob();
            }
        });

        pickup = (TextView) findViewById(R.id.pick_up_input);
        drop = (TextView) findViewById(R.id.drop_point_input);
        setpickupPoint();
    }

    private void setpickupPoint() {
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String resp = sharedPreferences.getString(Config.RESPONSE, "Not");
        Log.i("respoo", "ressss: " + resp);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resp);
            Log.i("jsonObj", "jsonObj:" + jsonObject);
            JSONArray result = null;
            result = jsonObject.getJSONArray("Result");
            Log.i("jsonarray","jsonarray:" + result);
            JSONObject parentobject = null;
            parentobject = new JSONObject(result.getString(0));
            Log.i("name","name: " + parentobject);
            String pickuppoint = parentobject.getString("pickuppoint");
            Log.i("pickup","pickup=="+pickuppoint);
            pickup.setText(pickuppoint);
            drop.setText(pickuppoint);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void storeTime(String time) {
        sharedPreferencesalert = getSharedPreferences("Alerts time", MODE_PRIVATE);
        //Creating editor to store values to shared preferences
        SharedPreferences.Editor editorAlert = sharedPreferencesalert.edit();
        editorAlert.putString("pickupAlert", time); // value to store
        editorAlert.apply();
    }

    public String getTime() {
        sharedPreferencesalert = getSharedPreferences("Alerts time", MODE_PRIVATE);
        String selectedTime = sharedPreferencesalert.getString("pickupAlert", String.valueOf(getResources().getColor(R.color.colorPrimary)));
        alertTimeselected = selectedTime;
        return selectedTime;
    }


    public void startJob() {
        Log.i("time","time=="+getTime());
        String Job_Tag = "my_job_tag";
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job job = jobDispatcher.newJobBuilder().
                setService(MyService.class).
                setLifetime(Lifetime.FOREVER).
                setRecurring(true).
                setTag(Job_Tag).
                setTrigger(Trigger.executionWindow(10,15)).
                setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                setReplaceCurrent(false).
                setConstraints(Constraint.ON_ANY_NETWORK)
                .build();
        jobDispatcher.mustSchedule(job);
        Toast.makeText(this,"Job Scheduled..",Toast.LENGTH_SHORT).show();
    }

    public void stopJob() {
        Log.i("timee","time==="+getTime());
        jobDispatcher.cancel(Job_Tag);
        Toast.makeText(this,"Job cancelled...",Toast.LENGTH_LONG).show();
    }
}
