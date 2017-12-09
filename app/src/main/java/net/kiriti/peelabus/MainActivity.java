package net.kiriti.peelabus;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    GridView simpleGrid;
    TextView parentName;
    TextView drawerName;

    private String urlJsonObj = "https://geofence-peelabus.herokuapp.com";
    private Timer timer = new Timer();
    private static String TAG = MainActivity.class.getSimpleName();
    Boolean inside;
    String position;
    ArrayList birdList=new ArrayList<>();

//    int logos[] = {R.mipmap.menu_track_bus, R.mipmap.menu_alerts, R.mipmap.menu_profiles, R.mipmap.menu_shareit,
//            R.mipmap.menu_help_feedback, R.mipmap.menu_logout};


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentName = (TextView) findViewById(R.id.parentname);

        geofence();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Config.EMAIL_SHARED_PREF,"Not Available");
        Log.i("name","name==" + email);
        parentName.setText(email);

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        simpleGrid = (GridView) findViewById(R.id.gridview);
        birdList.add(new Item("Track My Bus",R.mipmap.menu_track_bus));
        birdList.add(new Item("Alerts",R.mipmap.menu_alerts));
        birdList.add(new Item("Profile",R.mipmap.menu_profiles));
        birdList.add(new Item("Share",R.mipmap.menu_shareit));
        birdList.add(new Item("Help",R.mipmap.menu_help_feedback));
        birdList.add(new Item("Logout",R.mipmap.menu_logout));

        CustomAdapter myAdapter=new CustomAdapter(this,R.layout.main_button,birdList);
        simpleGrid.setAdapter(myAdapter);

//        simpleGrid = (GridView) findViewById(R.id.gridview); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
//        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), logos);
//        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                int idi = view.getId();
                Log.i("itemid","itemid == " + position);
                Log.i("itemidi","itemidi == " + idi);
                if (position == 0) {
                    Intent intent = new Intent(MainActivity.this, Track.class);
                    startActivity(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MainActivity.this, Alerts.class);
                    startActivity(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    String check = "Heyyy";
                    sendIntent.putExtra(Intent.EXTRA_TEXT,check);
                    Intent.createChooser(sendIntent,"Link here");
                    startActivity(Intent.createChooser(sendIntent,"Share using"));
                } else if (position == 4) {
//            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//            startActivity(intent);
                } else if (position == 5) {
                    logout();
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        drawerName = (TextView) headerView.findViewById(R.id.drawer_parentName);
        drawerName.setText(email);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void logout() {

        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        timer.cancel();
                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(Config.EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting activity
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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


    private void geofence() {
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {


                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        urlJsonObj, null, new Response.Listener<JSONObject>() {

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {

                            boolean insideLocal = response.getBoolean("insideradius");
//                            JSONObject obj = response.getJSONObject("insideradius");
                            inside = insideLocal;
                            Log.i("jsonarr", "jsonarr=" + inside);
                            String positionLocal = response.getString("enterorexit");
                            position = positionLocal;
                            Log.i("position","position==" + position);
                            // Parsing json object response
                            // response will be a json object
                            if (Objects.equals(position, "Enter")) {
//                                showNotification();
                                Log.i("notif","notif");
                                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Intent intent = new Intent(MainActivity.this, Track.class);
                                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                                Notification notification = new Notification.Builder(MainActivity.this)
                                        .setSound(soundUri)
                                        .setContentIntent(pendingIntent)
                                        .setSmallIcon(R.mipmap.menu_alerts)
                                        .setOnlyAlertOnce(true)
                                        .setColor(getResources().getColor(R.color.colorPrimary))
                                        .setPriority(Notification.PRIORITY_HIGH)
                                        .setContentTitle("Alert!!!")
                                        .setContentText(position+", Bus has reached the School")
                                        .addAction(R.mipmap.menu_alerts, "Open", pendingIntent)
                                        .setAutoCancel(true)
                                        .addAction(0, "Remind", pendingIntent).build();

                                NotificationManager notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notifManager.notify(0, notification);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();
                        // hide the progress dialog
                    }
                });
                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);
            }
        }, 0, 10 * 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }
        else if (id == R.id.notif) {
            return true;
        }
        else if (id == R.id.menuLogout) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_track) {
            Intent intent = new Intent(MainActivity.this, Track.class);
            startActivity(intent);
        } else if (id == R.id.nav_alerts) {
            Intent intent = new Intent(MainActivity.this, Alerts.class);
            startActivity(intent);
        } else if (id == R.id.nav_profiles) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            String check = "Heyyy";
            sendIntent.putExtra(Intent.EXTRA_TEXT,check);
            Intent.createChooser(sendIntent,"Share via");
            startActivity(Intent.createChooser(sendIntent,"Share using"));
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Track.class), 0);
        Resources r = getResources();
        Log.i("inside notif","inside notif");
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("PeelaBus")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Bus Position")
                .setContentText("Bus has reached the School")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
