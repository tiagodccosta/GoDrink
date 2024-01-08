package com.incheymus.godrink;

import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.util.List;

public class Place {

    private byte[] place_image;
    private String place_name, place_address;

    public Place(byte[] place_image, String place_name, String place_address) {
        this.place_image = place_image;
        this.place_name = place_name;
        this.place_address = place_address;
    }

    public byte[] getPlaceImage() {
        return place_image;
    }

    public String getPlaceName() {
        return place_name;
    }

    public String getPlaceAddress() {
        return place_address;
    }
}
