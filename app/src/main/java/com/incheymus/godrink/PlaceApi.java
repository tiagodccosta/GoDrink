package com.incheymus.godrink;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaceApi {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/places/";

    private final Context context;
    private final RequestQueue requestQueue;

    public PlaceApi(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public interface PlaceListener {
        void onPlacesReceived(List<Place> places);
        void onError(String message);
    }

    public void getPlacesByType(String type, PlaceListener listener) {
        String url = BASE_URL + type;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        List<Place> places = parseJsonResponse(response);
                        listener.onPlacesReceived(places);
                    } catch (JSONException e) {
                        Log.e("PlaceApi", "Error parsing JSON response", e);
                        listener.onError("Error parsing JSON response");
                    }
                },
                error -> {
                    Log.e("PlaceApi", "Error fetching data", error);
                    listener.onError("Error fetching data");
                });
        requestQueue.add(jsonArrayRequest);
    }

    private List<Place> parseJsonResponse(JSONArray jsonArray) throws JSONException {
        List<Place> places = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            // Check if the required fields are present in the JSON response
            if (jsonObject.has("placeName") && jsonObject.has("placeAddress")
                    && jsonObject.has("placeImage") && jsonObject.has("type")) {
                String name = jsonObject.getString("placeName");
                String address = jsonObject.getString("placeAddress");
                String type = jsonObject.getString("type");
                byte[] imageBytes = Base64.decode(jsonObject.getString("placeImage"), android.util.Base64.DEFAULT);

                Place place = new Place(imageBytes, name, address, type);
                places.add(place);
            } else {
                Log.e("PlaceApi", "Missing required fields for item at index " + i);
            }
        }
        return places;
    }
}
