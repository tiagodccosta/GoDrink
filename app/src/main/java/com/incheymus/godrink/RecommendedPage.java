package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecommendedPage extends AppCompatActivity {

    private Button btnGoBack;
    private CardView cardviewAfterParty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_page);

        btnGoBack = (Button) findViewById(R.id.btnGoBackToLandingPage);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecommendedPage.this, LandingPage.class));
            }
        });

        cardviewAfterParty = (CardView) findViewById(R.id.cardviewAfterParty);
        cardviewAfterParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecommendedPage.this, mapViewPage.class));
            }
        });
    }
}