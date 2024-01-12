package com.tiagocosta.GoDrink.Controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiagocosta.GoDrink.Models.Place;
import com.tiagocosta.GoDrink.Models.Repositories.PlaceRepository;

@RestController
@RequestMapping(path = "/api/places")
public class PlacesController {

    private Logger logger = LoggerFactory.getLogger(PlacesController.class);
    @Autowired
    private PlaceRepository placesRepository;

    @GetMapping(path = "", produces= MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Place> getPlaces() {
        logger.info("Sending all places");
        return placesRepository.findAll();
    }    

    @GetMapping("/{type}")
    public List<Place> getPlacesByType(@PathVariable String type) {
        logger.info("Sending places with type " + type);
        return placesRepository.findByType(type);
    }

}
