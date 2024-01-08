package com.incheymus.godrink;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.Place;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class RecommendedPage extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 42;
    private Button btnGoBack;
    private ListView preferencesSelectedInChooser;
    private TextView rangeSelectedPLACEHOLDER;
    private RecyclerView recyclerView;

    private ArrayList<String> selectedPreferences = null;
    private int rangeSelected;
    private PlacesAdapter adapter;
    private List<Place> places;
    String apiKey;
    private static final String TAG = "101";
    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_page);

        apiKey = getString(R.string.API_KEY);

        selectedPreferences = getIntent().getStringArrayListExtra("selectedList");
        rangeSelected = getIntent().getIntExtra("range", 15);

        rangeSelectedPLACEHOLDER = (TextView) findViewById(R.id.rangeSelectedPLACEHOLDER);
        rangeSelectedPLACEHOLDER.setText("In a range of " + rangeSelected + "KM");

        recyclerView = (RecyclerView) findViewById(R.id.recommendedPlaces);
        places = new ArrayList<>();

        btnGoBack = (Button) findViewById(R.id.btnGoBackToLandingPage);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecommendedPage.this, ChooserPage.class));
            }
        });

        setAdapter();
        setComponents();
        fetchPlacesFromDatabase();
    }

    public void setComponents() {
        //inflate ListView with selected preferences in Chooser Page
        preferencesSelectedInChooser = (ListView) findViewById(R.id.preferencesSelectedInChooser);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                R.layout.selected_preferences_list_view, R.id.selectedPreference, selectedPreferences);
        preferencesSelectedInChooser.setAdapter(arrayAdapter);
        ViewGroup.LayoutParams params = preferencesSelectedInChooser.getLayoutParams();
        params.height = 170 * selectedPreferences.size();
        preferencesSelectedInChooser.setLayoutParams(params);
    }

    public void setAdapter() {
        adapter = new PlacesAdapter(this, new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PlacesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(com.incheymus.godrink.Place place) {
                openGoogleMapsDirections(place.getPlaceAddress());
            }
        });
    }

    private void openGoogleMapsDirections(String address) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps app not installed", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchPlacesFromDatabase() {
        Log.d(TAG, "Fetching places from the database");
        PlaceApi placeApi = new PlaceApi(this);

        for (String preference : selectedPreferences) {
            placeApi.getPlacesByType(preference, new PlaceApi.PlaceListener() {
                public void onPlacesReceived(List<com.incheymus.godrink.Place> places) {
                    Log.d(TAG, "Places received for type " + preference + ": " + places.size());
                    adapter.removeDuplicatePlaces(places);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onError(String message) {
                    // Handle error
                    Log.e(TAG, "Error fetching places for type " + preference + ": " + message);
                }
            });
        }
    }
}