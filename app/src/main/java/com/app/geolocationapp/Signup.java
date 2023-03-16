package com.app.geolocationapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
//import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Signup extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    private static final String url = "http://192.168.1.39/LoginRegister/signup.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname, username, password, email;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());

                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";
                            String[] data = new String[4];
                            data[0] = fullname;
                            data[1] = username;
                            data[2] = password;
                            data[3] = email;

                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(Signup.this, response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Signup.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String , String> map = new HashMap<>();
                                    map.put("fullname",fullname);
                                    map.put("username",username);
                                    map.put("password",password);
                                    map.put("email",email);
                                    return map;
                                }
                            };

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(request);
//                            PutData putData = new PutData("http://192.168.1.10/LoginRegister/signup.php", "POST", field, data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
//                                    String result = putData.getResult();
//                                    if(result.equals("Sign Up Success")){
//                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), Login.class);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//                                    else {
//                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}