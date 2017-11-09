package net.kiriti.peelabus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class ProfileActivity extends AppCompatActivity {

    //Textview to show currently logged in user
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private NetworkImageView imageView;
    private NetworkImageView imageView1;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        //Initializing textview
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView = (NetworkImageView) findViewById(R.id.thumbnail);
        imageView1 = (NetworkImageView) findViewById(R.id.thumbnail1);



        //Fetching email from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");

        String resp = sharedPreferences.getString(Config.RESPONSE, "Not");
        Log.i("respoo", "ressss: " + resp);
        try {
            JSONObject jsonObject = new JSONObject(resp);
            Log.i("jsonObj", "jsonObj:" + jsonObject);
            JSONArray result = jsonObject.getJSONArray("Result");
            Log.i("jsonarray","jsonarray:" + result);
//            JSONObject reultin = new JSONObject(String.valueOf(result));
            if (result.length() == 1) {
            JSONObject parentobject = new JSONObject(result.getString(0));
            Log.i("name","name: " + parentobject);

                String child1name = parentobject.getString("studentname");
                String child1class = parentobject.getString("studentclass");
                String child1sect = parentobject.getString("section");
                String child1bus = parentobject.getString("busno");

            String parentname = parentobject.getString("parentname");
            Log.i("name","name: " + parentname);
            String parentmail = parentobject.getString("emailid");
            Log.i("name","name: " + parentmail);
            String alternatemobile = parentobject.getString("mothermobileno");
            Log.i("name","name: " + alternatemobile);

            String path = "http://admin.peelabus.com/" + parentobject.getString("imagepath");
            Log.i("name","name: " + path);

                SharedPreferences sharedPreferences1 = ProfileActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();

                //Adding values to editor
                editor.putString(Config.child1name, child1name);
                Log.i("nameit", "nameit=" + child1name);
                editor.putString(Config.child1class, child1class);
                editor.putString(Config.child1section, child1sect);
                editor.putString(Config.child1bus, child1bus);
                editor.putString(Config.child1pic, path);
                //Saving values to editor
                editor.apply();

            imageLoader = ImageRequest.getInstance(this.getApplicationContext())
                    .getImageLoader();
            imageLoader.get(path, ImageLoader.getImageListener(imageView,
                    R.drawable.logo, android.R.drawable
                            .ic_dialog_alert));
            imageView.setImageUrl(path, imageLoader);

            textView1.setText("Parent Name: " + parentname);
            textView2.setText("Email: " + parentmail);
            textView3.setText("Alternate Mobile Number: " + alternatemobile);
            } else {
                JSONObject parentobject = new JSONObject(result.getString(0));
                JSONObject parentobject1 = new JSONObject(result.getString(1));
                Log.i("name","name: " + parentobject);

                String child1name = parentobject.getString("studentname");
                String child1class = parentobject.getString("studentclass");
                String child1sect = parentobject.getString("section");
                String child1bus = parentobject.getString("busno");

                String child2name = parentobject1.getString("studentname");
                String child2class = parentobject1.getString("studentclass");
                String child2sect = parentobject1.getString("section");
                String child2bus = parentobject1.getString("busno");
                Log.i("bus","bus=" + child2bus);

                String parentname = parentobject.getString("parentname");
                Log.i("name","name: " + parentname);
                String parentmail = parentobject.getString("emailid");
                Log.i("name","name: " + parentmail);
                String alternatemobile = parentobject.getString("mothermobileno");
                Log.i("name","name: " + alternatemobile);

                String path = "http://admin.peelabus.com/" + parentobject.getString("imagepath");
                Log.i("name","name: " + path);

                String path1 = "http://admin.peelabus.com/" + parentobject1.getString("imagepath");
                Log.i("child2","child2 " + path1);

                SharedPreferences sharedPreferences1 = ProfileActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();

                //Adding values to editor
                editor.putString(Config.child1name, child1name);
                Log.i("nameit", "nameit=" + child1name);
                editor.putString(Config.child1class, child1class);
                editor.putString(Config.child1section, child1sect);
                editor.putString(Config.child1bus, child1bus);

                editor.putString(Config.child2name, child2name);
                editor.putString(Config.child2class, child2class);
                editor.putString(Config.child2section, child2sect);
                editor.putString(Config.child2bus, child2bus);

                editor.putString(Config.child1pic, path);
                editor.putString(Config.child2pic, path1);
                //Saving values to editor
                editor.apply();

                imageLoader = ImageRequest.getInstance(this.getApplicationContext())
                        .getImageLoader();
                imageLoader.get(path, ImageLoader.getImageListener(imageView,
                        R.drawable.logo, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(path, imageLoader);

                imageLoader = ImageRequest.getInstance(this.getApplicationContext())
                        .getImageLoader();
                imageLoader.get(path1, ImageLoader.getImageListener(imageView1,
                        R.drawable.logo, android.R.drawable
                                .ic_dialog_alert));
                imageView1.setImageUrl(path1, imageLoader);

                textView1.setText("Parent Name: " + parentname);
                textView2.setText("Email: " + parentmail);
                textView3.setText("Alternate Mobile Number: " + alternatemobile);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Showing the current logged in email to textview
        textView.setText("Mobile Number: " + email);

//        textView1.setText("Current User: " + resp);
    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menuLogout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void childPro(View view) {
        Intent intent = new Intent(ProfileActivity.this, ChildActivity.class);
        intent.putExtra("child1", true);
        startActivity(intent);
    }

    public void child2Pro(View view) {
        Intent intent = new Intent(ProfileActivity.this, ChildActivity.class);
        intent.putExtra("child2", true);
        startActivity(intent);
    }
}
