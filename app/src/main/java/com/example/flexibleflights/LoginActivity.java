package com.example.flexibleflights;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flexibleflights.databinding.ActivityLoginBinding;
import com.example.flexibleflights.ui.login.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = binding.emailEditText;
        final EditText passwordEditText = binding.passwordEditText;
        final Button loginButton = binding.loginButton;
        //final ProgressBar loadingProgressBar = binding.loading;
        final TextView guestLoginText = binding.guestLoginTextView;
        final TextView registerText = binding.returnTextView;

        //
        //Establishing Node.js server connection
        //
        //final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://54.163.192.205:3000";                     //TODO Make sure url is matching EC2 instance ip, it may change on reboot
        //queue.start();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(usernameEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // Did we find a matching account?
                            // Successful Register
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class)); // Run main feed

                        }
                        else{ // Unsuccessful register
                            Toast.makeText(LoginActivity.this, "Failed to register", Toast.LENGTH_LONG).show(); // Error, invalid login.
                        }
                    }
                });
            }
        });
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redirects to register activity
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        guestLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redirect to MainActivity
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Signed in as guest", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUiWithUser(String name) {
        String welcome = getString(R.string.welcome) + name;
        // TODO : initiate successful logged in experience (go to main activity)
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
    public void login(String url, String username, String password){
        //RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
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

                //Authenticate message from server to determine login status
                authenticateLogin(message);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
            }
        });
        //queue.add(jsonObjectRequest);
    }
    public void authenticateLogin(String message){
        //TODO: check response from server and log user in
        if(message == "1"){
            //user login successful
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        else {
            Toast.makeText(getApplicationContext(), "Error logging in", Toast.LENGTH_SHORT).show();
        }
    }

}

