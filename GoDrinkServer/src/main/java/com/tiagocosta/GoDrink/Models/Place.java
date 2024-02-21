package com.tiagocosta.GoDrink.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "places")
public class Place {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="place_id") private int id;
    @Column(name="place_name") private String place_name;
    @Column(name="place_address") private String place_address;
    @Column(name="type") private String type;
    @Lob
    @Column(name = "place_image", columnDefinition = "BLOB")
    private byte[] place_image;
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPlaceName() {
        return place_name;
    }
    public void setPlaceName(String place_name) {
        this.place_name = place_name;
    }
    public String getPlaceAddress() {
        return place_address;
    }
    public void setPlaceAddress(String place_address) {
        this.place_address = place_address;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public byte[] getPlaceImage() {
        return place_image;
    }
    public void setPlaceImage(byte[] place_image) {
        this.place_image = place_image;
    }
}
