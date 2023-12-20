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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class RecommendedPage extends AppCompatActivity {

    private Button btnGoBack;
    private CardView cardviewAfterParty;
    private CardView cardviewKaraokeBar;
    private TextView addressAfterParty;
    private TextView addressKaraokeBar;
    private ListView preferencesSelectedInChooser;
    private TextView rangeSelectedPLACEHOLDER;

    private ArrayList<String> selectedPreferences = null;
    private int rangeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_page);

        selectedPreferences = getIntent().getStringArrayListExtra("selectedList");
        rangeSelected = getIntent().getIntExtra("range", 15);

        rangeSelectedPLACEHOLDER = (TextView) findViewById(R.id.rangeSelectedPLACEHOLDER);
        rangeSelectedPLACEHOLDER.setText("In a range of " + rangeSelected + "KM");

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

        preferencesSelectedInChooser = (ListView) findViewById(R.id.preferencesSelectedInChooser);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.selected_preferences_list_view, R.id.selectedPreference, selectedPreferences);
        preferencesSelectedInChooser.setAdapter(arrayAdapter);
        ViewGroup.LayoutParams params = preferencesSelectedInChooser.getLayoutParams();
        params.height = 170 * selectedPreferences.size();
        preferencesSelectedInChooser.setLayoutParams(params);
    }
}