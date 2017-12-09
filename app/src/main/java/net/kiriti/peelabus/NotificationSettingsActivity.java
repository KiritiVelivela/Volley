package net.kiriti.peelabus;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Switch;

import net.kiriti.peelabus.R;

import java.io.IOException;

import java.io.IOException;

public class NotificationSettingsActivity extends AppCompatActivity {

    private Switch vibration_2,vibration_1,Sound_1,Sound_2;

    private SharedPreferences SchoolToHomeVibrationPref,SchoolToHomeSoundPref,HomeToSchoolVibrationPref,HomeToSchoolSoundPref;

    private String SchoolToHomeVibrationPref_Key,SchoolToHomeSoundPref_key,HomeToSchoolVibrationPref_key,HomeToSchoolSoundPref_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HomeToSchoolSoundPref_key = "HomeToSchoolSoundPref_Key";
        SchoolToHomeVibrationPref_Key = "SchoolToHomeVibrationPref_Key";
        SchoolToHomeSoundPref_key = "SchoolToHomeSoundPref_key";
        HomeToSchoolVibrationPref_key = "HomeToSchoolVibrationPref_key";

        SchoolToHomeSoundPref = PreferenceManager.getDefaultSharedPreferences(this);
        SchoolToHomeVibrationPref = PreferenceManager.getDefaultSharedPreferences(this);
        HomeToSchoolSoundPref = PreferenceManager.getDefaultSharedPreferences(this);
        HomeToSchoolSoundPref = PreferenceManager.getDefaultSharedPreferences(this);


        vibration_1 = (Switch) findViewById(R.id.vibration_1);

        vibration_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVibration_1(view);
            }
        });

        vibration_2 = (Switch) findViewById(R.id.vibration_2);
        vibration_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkVibration_2(view);
            }
        });
        Sound_1 = (Switch) findViewById(R.id.sound_1);
        Sound_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSound_1(view);
            }
        });
        Sound_2 = (Switch) findViewById(R.id.sound_2);

        Sound_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkSound_2(view);
            }
        });


    }

    public void checkSound_2(View view){
        if (Sound_1.isChecked()){
            // TODO: Ring the device and set preference.
            Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.setDataSource(this, defaultRingtoneUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        mp.release();
                    }
                });
                mediaPlayer.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // TODO: remove preference
        }
    }

    public void checkVibration_1(View view){
        if (vibration_1.isChecked()){
            // TODO: Vibrate device and set preference.
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        } else {
            // TODO: remove preference
        }

    }

    public void checkVibration_2(View view){
        if (vibration_2.isChecked()){
            // TODO: Vibrate device and set preference.
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(200);
        } else {
            // TODO: remove preference
        }
    }

    public void checkSound_1(View view){
        if (Sound_1.isChecked()){
            // TODO: Ring device and set preference.
            Uri defaultRingtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            MediaPlayer mediaPlayer = new MediaPlayer();

            try {
                mediaPlayer.setDataSource(this, defaultRingtoneUri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                mediaPlayer.prepare();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        mp.release();
                    }
                });
                mediaPlayer.start();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // TODO: remove preference
        }
    }

}
