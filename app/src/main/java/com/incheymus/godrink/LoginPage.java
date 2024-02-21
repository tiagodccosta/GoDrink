package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPage extends AppCompatActivity {

    private Button btnSignUp;
    private Button btnLogin;
    private EditText emailInput;
    private EditText passwordInput;
    private String nameOfUserLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailInput = (EditText) findViewById(R.id.emailFieldInput);
        passwordInput = (EditText) findViewById(R.id.passFieldInput);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail(emailInput.getText().toString())) {
                    emailInput.setError("Invalid Email");
                } else if (!validatePassword(passwordInput.getText().toString())) {
                    passwordInput.setError("Invalid Password!\n" + "At least 9 letters");
                } else {
                    loginUser();
                }
            }
        });
    }

    public void loginUser() {
        RequestQueue queue = Volley.newRequestQueue(LoginPage.this);
        String LOCALHOST = "http://10.0.2.2:2599/api/users/login";

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", emailInput.getText().toString());
        params.put("password", passwordInput.getText().toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, LOCALHOST, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nameLoggedIn = (String) response.get("userName");
                            nameOfUserLoggedIn = nameLoggedIn;
                            Intent intent = new Intent(LoginPage.this, LandingPage.class);
                            intent.putExtra("userName", nameOfUserLoggedIn);
                            startActivity(intent);
                            Toast.makeText(LoginPage.this, "User Logged In", Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Toast.makeText(LoginPage.this, "Network Error", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(LoginPage.this, "Server Error: " + error.networkResponse.statusCode, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginPage.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
                error.printStackTrace();
                Toast.makeText(LoginPage.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    protected boolean validateEmail(String email) {
        String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`" +
                "{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
                "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*" +
                "[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
                "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-" +
                "\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected Boolean validatePassword(String password){
        if(!(password.isEmpty()) && password.length() > 8) {
            return true;
        } else {
            return false;
        }
    }
}