package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpPage extends AppCompatActivity {


    private Button btnSignUp;
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;

    private String name;
    private Boolean success = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        nameInput = (EditText) findViewById(R.id.nameFieldInput);
        emailInput = (EditText) findViewById(R.id.emailFieldInput);
        passwordInput = (EditText) findViewById(R.id.passFieldInput);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName()) {
                    nameInput.setError("Invalid Name!");
                } else if (!validateEmail(emailInput.getText().toString())) {
                    emailInput.setError("Invalid Email!");
                    emailInput.requestFocus();
                } else if (!validatePassword(passwordInput.getText().toString())) {
                    passwordInput.setError("Invalid Password!\n" +
                            "At least 9 letters");
                    passwordInput.requestFocus();
                } else {
                    registerUser();
                    startActivity(new Intent(SignUpPage.this, LoginPage.class));
                }
            }
        });
    }

    public void registerUser() {
        RequestQueue queue = Volley.newRequestQueue(SignUpPage.this);
        String LOCALHOST = "http://10.0.2.2:8080/api/users/register";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOCALHOST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equalsIgnoreCase("success")) {
                    nameInput.setText(null);
                    emailInput.setText(null);
                    passwordInput.setText(null);
                    Toast.makeText(SignUpPage.this, "Registration Successful", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpPage.this, "Registration Unsuccessful", Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_name", nameInput.getText().toString());
                params.put("user_email", emailInput.getText().toString());
                params.put("password", passwordInput.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
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

    protected Boolean validateName(){

        name = nameInput.getText().toString();

        if(!(name.isEmpty()) && name.length() > 2) {
            return true;
        } else {
            return false;
        }
    }


}