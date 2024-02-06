package com.example.flexibleflights;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    TextView RequestText;
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

        ////
        //Node.js server handling
        ////
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://54.147.221.40:3000";
        final String postUrl = "http://54.147.221.40:3000/postdata";

        testConnection(RequestText, url);

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
                testPostConnection(postUrl, RequestText);
            }
        });

    }

    public void testConnection(TextView textView, String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://54.172.240.154:3000/";

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

//TODO - fix JSON object passing, above function can read out but does not properly pass
    public void JsonTest(String url, TextView textView) throws JSONException {

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JSONObject jsonParams = new JSONObject();
        jsonParams.put("name", "Sean");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonParams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // on below line we are parsing the response
                // to json object to extract data from it.
                JSONObject respObj = new JSONObject((Map) response);

                // below are the strings which we
                // extract from our json object.
                String message = null;
                try {
                    message = respObj.getString("message");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                // on below line we are setting this string s to our text view.
                textView.setText(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

}