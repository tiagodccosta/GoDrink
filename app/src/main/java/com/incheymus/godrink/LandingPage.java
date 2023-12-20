package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LandingPage extends AppCompatActivity {

    private Button btnLogOut;
    private Button btnStartChooser;
    private TextView welcomePlaceHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        String user_name = getIntent().getStringExtra("user_name");

        btnLogOut = (Button) findViewById(R.id.btnLogOUT);
        btnStartChooser = (Button) findViewById(R.id.btnStart);

        welcomePlaceHolder = (TextView) findViewById(R.id.welcomePLACEHOLDER);
        welcomePlaceHolder.setText("welcome, " + user_name);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, LoginPage.class));
            }
        });

        btnStartChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LandingPage.this, ChooserPage.class));
            }
        });

    }
}