package com.example.flexibleflights;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flexibleflights.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_register);

        final EditText usernameEditText = binding.emailEditText;
        final EditText passwordEditText = binding.passwordEditText;
        final Button registerButton = binding.registerButton;
        final TextView returnText = binding.returnTextView;

        //
        //Establishing Node.js server connection
        //
        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://54.163.192.205:3000";                     //TODO Make sure url is matching EC2 instance ip, it may change on reboot
        queue.start();

        returnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                register((url + "/register"), username, password);
            }
        });
    }



    public void register(String url, String username, String password){
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
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
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error registering", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void authenticateRegister(String message){
        if(message == "1"){
            //user register successful
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_SHORT).show();
        } else if (message == "2") {
            Toast.makeText(getApplicationContext(), "Error Registering, email already in use", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error Registering", Toast.LENGTH_SHORT).show();
        }
    }
}