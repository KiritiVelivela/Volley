package net.kiriti.peelabus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChildActivity extends AppCompatActivity {

    private TextView childbus;
    private TextView childname;
    private TextView childemail;
    private TextView childsection;
    private CircleImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initializing textview
        childbus = (TextView) findViewById(R.id.childbus);
        childname = (TextView) findViewById(R.id.childname);
        childemail = (TextView) findViewById(R.id.childemail);
        childsection = (TextView) findViewById(R.id.childsection);
        imageView = (CircleImageView) findViewById(R.id.childprofile);

        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        Boolean first = bundle.getBoolean("child1");
        Boolean sec = bundle.getBoolean("child2");
        Log.i("check", "check=" + first);
        Log.i("check2", "chec2k=" + sec);

        if(first == true) {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String resp = sharedPreferences.getString(Config.RESPONSE, "Not");
            Log.i("respoo", "ressss: " + resp);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(resp);
                Log.i("jsonObj", "jsonObj:" + jsonObject);
                JSONArray result = jsonObject.getJSONArray("Result");
                Log.i("jsonarray","jsonarray:" + result);
//            JSONObject reultin = new JSONObject(String.valueOf(result));

                //IF///////////////////
//                if (result.length() == 1) {
                    JSONObject parentobject = new JSONObject(result.getString(0));
                    Log.i("name","name: " + parentobject);

                    String child1name = parentobject.getString("studentname");
                    String child1class = parentobject.getString("studentclass");
                    String child1sect = parentobject.getString("section");
                    String child1bus = parentobject.getString("busno");
                    String path = "http://admin.peelabus.com/" + parentobject.getString("imagepath");
                    Log.i("name","name: " + path);

                    Picasso.with(getApplicationContext()).load(path)
                            .placeholder(R.drawable.splash_bg).error(R.drawable.changepassword_bg)
                            .into(imageView);

                    childname.setText("Name: " + child1name);
                    childemail.setText("Class: " + child1class);
                    childsection.setText("Section: " + child1sect);
                    childbus.setText("Bus: " + child1bus);

             } catch (JSONException e) {
                e.printStackTrace();
            }

//            String name = sharedPreferences.getString(Config.child1name,"Not Available");
//            Log.i("name1", "name1=" + sharedPreferences.getString(Config.child1name,"Not Available"));
//            String sect = sharedPreferences.getString(Config.child1section, "Not");
//            Log.i("sec1", "sec1=" + sect);
//            String clas = sharedPreferences.getString(Config.child1class, "Not");
//            Log.i("clas1", "clas1=" + clas);
//            String bus = sharedPreferences.getString(Config.child1bus, "Not");
//            Log.i("bus1", "bus1=" + bus);
//            String path = sharedPreferences.getString(Config.child1pic, "Not");
//            Log.i("pic1", "pic1=" + path);


        } else {

            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            String resp = sharedPreferences.getString(Config.RESPONSE, "Not");
            Log.i("respoo", "ressss: " + resp);

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(resp);
                Log.i("jsonObj", "jsonObj:" + jsonObject);
                JSONArray result = jsonObject.getJSONArray("Result");
                Log.i("jsonarray","jsonarray:" + result);
//            JSONObject reultin = new JSONObject(String.valueOf(result));

                //IF///////////////////
//                if (result.length() == 1) {
                JSONObject parentobject = new JSONObject(result.getString(1));
                Log.i("name","name: " + parentobject);

                String child1name = parentobject.getString("studentname");
                String child1class = parentobject.getString("studentclass");
                String child1sect = parentobject.getString("section");
                String child1bus = parentobject.getString("busno");
                String path = "http://admin.peelabus.com/" + parentobject.getString("imagepath");
                Log.i("name","name: " + path);

                Picasso.with(getApplicationContext()).load(path)
                        .placeholder(R.drawable.splash_bg).error(R.drawable.changepassword_bg)
                        .into(imageView);

                childname.setText("Name: " + child1name);
                childemail.setText("Class: " + child1class);
                childsection.setText("Section: " + child1sect);
                childbus.setText("Bus: " + child1bus);

            } catch (JSONException e) {
                e.printStackTrace();
            }

//            String name = sharedPreferences.getString(Config.child2name,"Not Available");
//            Log.i("name2", "name2=" + name);
//            String sect = sharedPreferences.getString(Config.child2section, "Not");
//            Log.i("clas2", "clas2=" + sect);
//            String clas = sharedPreferences.getString(Config.child2class, "Not");
//            Log.i("clas2", "clas2=" + clas);
//            String bus = sharedPreferences.getString(Config.child2bus, "Not");
//            Log.i("bus2", "bus2=" + bus);
//            String path = sharedPreferences.getString(Config.child2pic, "Not");
//            Log.i("pic2", "pic2=" + path);

//            imageLoader = ImageRequest.getInstance(this.getApplicationContext())
//                    .getImageLoader();
//            imageLoader.get(path, ImageLoader.getImageListener(imageView,
//                    R.drawable.logo, android.R.drawable
//                            .ic_dialog_alert));
//            imageView.setImageUrl(path, imageLoader);

//            Picasso.with(getApplicationContext()).load(path)
//                    .placeholder(R.drawable.splash_bg).error(R.drawable.changepassword_bg)
//                    .into(imageView);
//
//            childname.setText("Name: " + name);
//            childemail.setText("Class: " + clas);
//            childsection.setText("Section: " + sect);
//            childbus.setText("Bus: " + bus);

        }


    }
}
