package com.incheymus.godrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RecommendedPage extends AppCompatActivity {

    private RecyclerView recyclerView;

    private ArrayList<String> selectedPreferences = null;
    private PlacesAdapter adapter;
    String apiKey;
    private static final String TAG = "101";
    private int rangeSelected;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;
    private double userLatitude;
    private double userLongitude;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_page);

        apiKey = getString(R.string.API_KEY);

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Request location permissions if not granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permissions already granted, get user's location
            getUserLocation();
        }

        selectedPreferences = getIntent().getStringArrayListExtra("selectedList");
        rangeSelected = getIntent().getIntExtra("range", 15);

        TextView rangeSelectedPLACEHOLDER = (TextView) findViewById(R.id.rangeSelectedPLACEHOLDER);
        rangeSelectedPLACEHOLDER.setText("In a range of " + rangeSelected + "KM");

        recyclerView = (RecyclerView) findViewById(R.id.recommendedPlaces);
        List<Place> places = new ArrayList<>();

        Button btnGoBack = (Button) findViewById(R.id.btnGoBackToLandingPage);
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecommendedPage.this, ChooserPage.class));
            }
        });

        setAdapter();
        setComponents();
        fetchPlacesFromDatabase();
        getUserLocation();
    }

    public void setComponents() {
        //inflate ListView with selected preferences in Chooser Page
        ListView preferencesSelectedInChooser = (ListView) findViewById(R.id.preferencesSelectedInChooser);
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

        adapter.clearPlaces();

        for (String preference : selectedPreferences) {
            placeApi.getPlacesByType(preference, new PlaceApi.PlaceListener() {
                @Override
                public void onPlacesReceived(List<com.incheymus.godrink.Place> places) {
                    Log.d(TAG, "Places received for type " + preference + ": " + places.size());

                    // Calculate distance for each place and filter based on the selected range
                    List<com.incheymus.godrink.Place> filteredPlaces = new ArrayList<>();
                    for (com.incheymus.godrink.Place place : places) {
                        LatLng latLng = getLatLngFromAddress(place.getPlaceAddress());
                        if (latLng != null) {
                            Log.d(TAG, "Place latitude and longitude: " + latLng.latitude + " " + latLng.longitude);
                            Log.d(TAG, "User Latitude and Longitude: " + userLatitude + " " + userLongitude);
                            double distance = calculateDistance(userLatitude, userLongitude, latLng.latitude, latLng.longitude);
                            place.setDistanceFromUser(distance);
                            Log.d(TAG, "Distance from the place: " + distance);
                            if (distance <= rangeSelected) {
                                filteredPlaces.add(place);
                            }
                        } else {
                            Log.e(TAG, "Failed to retrieve latitude and longitude for address: " + place.getPlaceAddress());
                        }
                    }
                    Log.d(TAG, "Filtered places: " + filteredPlaces.size());
                    // Update adapter with filtered places
                    adapter.removeDuplicatePlaces(filteredPlaces);
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onError(String message) {
                    Log.e(TAG, "Error fetching places for type " + preference + ": " + message);
                }
            });
        }
    }

    public LatLng getLatLngFromAddress(String address) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                double latitude = addresses.get(0).getLatitude();
                double longitude = addresses.get(0).getLongitude();
                return new LatLng(latitude, longitude);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371; // in kilometers

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    private void getUserLocation() {
        // Check if location permissions are granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            // Get user's latitude and longitude
                            userLatitude = location.getLatitude();
                            userLongitude = location.getLongitude();
                        } else {
                            // Handle case where location is null
                            Toast.makeText(RecommendedPage.this, "Failed to get location", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Handle case where location permissions are not granted
            Toast.makeText(this, "Location permissions not granted", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
                getUserLocation();
            } else {
                // Permissions not granted
                Toast.makeText(this, "Location permissions denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}