package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class RecommendedPage extends AppCompatActivity {

    private Button btnGoBack;
    private CardView cardviewAfterParty;
    private CardView cardviewKaraokeBar;
    private TextView addressAfterParty;
    private TextView addressKaraokeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_page);

        addressAfterParty = (TextView) findViewById(R.id.addressPLACEHOLDER);
        addressKaraokeBar = (TextView) findViewById(R.id.addressPLACEHOLDER2);
        btnGoBack = (Button) findViewById(R.id.btnGoBackToLandingPage);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecommendedPage.this, ChooserPage.class));
            }
        });

        cardviewAfterParty = (CardView) findViewById(R.id.cardviewAfterParty);
        cardviewAfterParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = addressAfterParty.getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + destination + "&mode=w"));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cardviewKaraokeBar = (CardView) findViewById(R.id.cardViewKaraokeBar);
        cardviewKaraokeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String destination = addressKaraokeBar.getText().toString();

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + destination + "&mode=w"));
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}