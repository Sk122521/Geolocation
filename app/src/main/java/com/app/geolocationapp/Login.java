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

public class Login extends AppCompatActivity {


    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    private static final String url = "http://192.168.1.39/loginRegister/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullname, username, password, email;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());

                if(!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;

                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String , String> map = new HashMap<>();

                                    map.put("username",username);
                                    map.put("password",password);

                                    return map;
                                }
                            };

                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(request);


//                            PutData putData = new PutData("http://192.168.1.5/login/login.php", "POST", field, data);
//                            if (putData.startPut()) {
//                                if (putData.onComplete()) {
//                                    progressBar.setVisibility(View.GONE);
//                                    String result = putData.getResult();
//                                    if(result.equals("Login Success")){
//                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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