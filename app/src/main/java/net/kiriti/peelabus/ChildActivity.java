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

public class ChildActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private NetworkImageView imageView;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        //Initializing textview
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (NetworkImageView) findViewById(R.id.thumbnail);

        Intent intent2 = getIntent();
        Bundle bundle = intent2.getExtras();
        Boolean first = bundle.getBoolean("child1");
        Boolean sec = bundle.getBoolean("child2");
        Log.i("check", "check=" + first);
        Log.i("check2", "chec2k=" + sec);

        if(first == true) {
            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String name = sharedPreferences.getString(Config.child1name,"Not Available");
            Log.i("name1", "name1=" + name);
            String sect = sharedPreferences.getString(Config.child1section, "Not");
            Log.i("clas1", "clas1=" + sect);
            String clas = sharedPreferences.getString(Config.child1class, "Not");
            Log.i("clas1", "clas1=" + clas);
            String bus = sharedPreferences.getString(Config.child1bus, "Not");
            Log.i("bus1", "bus1=" + bus);
            String path = sharedPreferences.getString(Config.child1pic, "Not");
            Log.i("pic1", "pic1=" + path);

            imageLoader = ImageRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(path, ImageLoader.getImageListener(imageView,
                    R.drawable.pilabuslogocopy, android.R.drawable
                            .ic_dialog_alert));
            imageView.setImageUrl(path, imageLoader);

            textView1.setText("Name: " + name);
            textView2.setText("Email: " + clas);
            textView3.setText("Section: " + sect);
            textView.setText("Bus: " + bus);

        } else {

            SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String name = sharedPreferences.getString(Config.child2name,"Not Available");
            Log.i("name1", "name1=" + name);
            String sect = sharedPreferences.getString(Config.child2section, "Not");
            Log.i("clas1", "clas1=" + sect);
            String clas = sharedPreferences.getString(Config.child2class, "Not");
            Log.i("clas1", "clas1=" + clas);
            String bus = sharedPreferences.getString(Config.child2bus, "Not");
            Log.i("bus1", "bus1=" + bus);
            String path = sharedPreferences.getString(Config.child2pic, "Not");
            Log.i("pic1", "pic1=" + path);

            imageLoader = ImageRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(path, ImageLoader.getImageListener(imageView,
                    R.drawable.pilabuslogocopy, android.R.drawable
                            .ic_dialog_alert));
            imageView.setImageUrl(path, imageLoader);

            textView1.setText("Name: " + name);
            textView2.setText("Email: " + clas);
            textView3.setText("Section: " + sect);
            textView.setText("Bus: " + bus);

        }


    }
}
