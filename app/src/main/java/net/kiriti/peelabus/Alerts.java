package net.kiriti.peelabus;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Alerts extends AppCompatActivity {

    TextView pickup;
    TextView drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pickup = (TextView) findViewById(R.id.pick_up_input);
        drop = (TextView) findViewById(R.id.drop_point_input);

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
}
