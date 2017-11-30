package net.kiriti.peelabus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Alerts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
