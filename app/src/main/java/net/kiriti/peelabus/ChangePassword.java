package net.kiriti.peelabus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener {

    private EditText usern;
    private EditText current;
    private EditText newpass;
    private EditText confirmpass;
    private Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usern = (EditText) findViewById(R.id.user);
        current = (EditText) findViewById(R.id.current);
        newpass = (EditText) findViewById(R.id.newpass);
        confirmpass = (EditText) findViewById(R.id.confirmnew);


        confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        changepassword();
    }

    private void changepassword() {

        final String txtUser = usern.getText().toString().trim();
        final String txtCurrPass = current.getText().toString().trim();
        final String txtNewPass = newpass.getText().toString().trim();
        final String txtConfirmNewPass = confirmpass.getText().toString().trim();

        Log.i("hey", "hey: " + txtCurrPass);
        Log.i("hey", "hey: " + txtNewPass);
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CHANGE_PASSWORD_URL,

                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i("resp1", "resp1: " + response);
                        //If we are getting success from server
                        if(!response.equalsIgnoreCase(Config.LOGIN_SUCCESS)){
                            //Creating a shared preference
                            Log.i("resp", "resp: " + response);
                            SharedPreferences sharedPreferences = ChangePassword.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putString(Config.USER, txtUser);
                            editor.putString(Config.CURRENT, txtCurrPass);
                            editor.putString(Config.EMAIL_SHARED_PREF, txtNewPass);
                            editor.putString(Config.RESPONSE, txtConfirmNewPass);

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(ChangePassword.this, LoginActivity.class);
                            startActivity(intent);
                        }else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(ChangePassword.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
//                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put(Config.USER, txtUser);
                params.put(Config.CURRENT, txtCurrPass);
                params.put(Config.EMAIL_SHARED_PREF, txtNewPass);
                params.put(Config.RESPONSE, txtConfirmNewPass);
                Log.i("par","par==" + params.put(Config.RESPONSE, txtConfirmNewPass));

                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        Log.i("req", "req==" + stringRequest);
    }

    }

