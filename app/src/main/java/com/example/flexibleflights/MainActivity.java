package com.example.flexibleflights;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.flexibleflights.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    Button RequestButton;
    Button LoginButton;
    Button RegisterButton;
    TextView RequestText;
    TextView LoginResultsText;
    EditText UsernameEditText;
    EditText PasswordEditText;

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        ////
        //Assigning layout vars
        ////
        RequestButton = (Button) findViewById(R.id.requestButton);
        RequestText = findViewById(R.id.requestText);

        //Login test vars
        LoginButton = (Button) findViewById(R.id.buttonLogin);
        RegisterButton = (Button) findViewById(R.id.buttonRegister);
        LoginResultsText = findViewById(R.id.textViewLogin);
        UsernameEditText = findViewById(R.id.editTextUsername);
        PasswordEditText = findViewById(R.id.editTextPassword);

        ////
        //Node.js server handling
        ////
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://44.220.135.168:3000";                     //TODO Make sure url is matching EC2 instance ip, it may change on reboot
        final String postUrl = url + "/postdata";
        //final String postUrl = "http://172.31.25.139:3000/postdata";

        //testConnection(RequestText, url);

        queue.start();



        RequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //HashMap<String, String> params = new HashMap<String,String>(); //JSON object to be passed to Node.js
                //params.put("data", "test"); //(title), (data)
                /*
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST, url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    RequestText.setText(response.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        RequestText.setText(error.toString());
                    }
                });
                queue.add(jsObjRequest);
                */
                //testPostConnection(url, RequestText);
                try {
                    JsonTest(postUrl, RequestText);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login((url + "/login"), UsernameEditText.getText().toString(), PasswordEditText.getText().toString(), LoginResultsText);
                UsernameEditText.setText("");
                PasswordEditText.setText("");
            }
        });

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login((url + "/register"), UsernameEditText.getText().toString(), PasswordEditText.getText().toString(), LoginResultsText);
                UsernameEditText.setText("");
                PasswordEditText.setText("");
            }
        });

    }

    public void testConnection(TextView textView, String url){
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText("Connection established!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void testPostConnection(String url, TextView textView){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    // on below line we are parsing the response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String message = respObj.getString("message");

                    // on below line we are setting this string s to our text view.
                    textView.setText(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                textView.setText(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", "sean");

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(request);
    }


    public void JsonTest(String url,TextView textView) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", "Sean");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // below are the strings which we
                // extract from our json object.
                String message = null;
                try {
                    message = response.getString("message");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // on below line we are setting this string s to our text view.
                textView.setText(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText(error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void login(String url, String username, String password, TextView textView){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // below are the strings which we
                // extract from our json object.
                String message = null;
                try {
                    message = response.getString("message");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // on below line we are setting this string s to our text view.
                textView.setText(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Error log in");
            }
        });
        queue.add(jsonObjectRequest);
    }



}