package net.kiriti.peelabus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private Button pass;
    private Button notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notif = (Button) findViewById(R.id.notificationset);
        pass = (Button) findViewById(R.id.change);

        notif.setOnClickListener(this);
        pass.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.notif) {
            Log.i("hey notif", "notif===");
        } else if (id == R.id.change) {
            Intent intent = new Intent(this, ChangePassword.class);
            startActivity(intent);
        }
    }
}
