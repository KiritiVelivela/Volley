package net.kiriti.peelabus;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by kiriti_sai on 11/12/17.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyService extends JobService {

    BackgroundTask backgroundTask;
//    String testTime = "05 mins";

    @Override
    public boolean onStartJob(final JobParameters job) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(this, Track.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);


        if (Alerts.alertTimeselected == DirectionFinder.eta) {
        Notification notification = new Notification.Builder(this)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.menu_alerts)
                .setOnlyAlertOnce(true)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("Alert!!!")
                .setContentText(", from JObbbb....")
                .addAction(R.mipmap.menu_alerts, "Open", pendingIntent)
                .setAutoCancel(true)
                .addAction(0, "Remind", pendingIntent).build();

        NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifManager.notify(0, notification);
        } else {
            Log.i("Not equal","Not equal::"+Alerts.alertTimeselected +"!="+DirectionFinder.eta);
        }
        backgroundTask = new BackgroundTask()
        {
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),"Message from Background Task :"+s,Toast.LENGTH_LONG).show();
                jobFinished(job,false);
            }
        };

        backgroundTask.execute();
        return true;
    }



    @Override
    public boolean onStopJob(JobParameters job) {
        return true;
    }


    public static class BackgroundTask extends AsyncTask<Void,Void,String>
    {

        @Override
        protected String doInBackground(Void... voids) {
            return "Hello from background job";
        }
    }

}
