package com.tiagocosta.GoDrink.Models.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.tiagocosta.GoDrink.Models.Place;

public interface PlaceRepository extends CrudRepository<Place, Integer> {
    List<Place> findByType(String type);
}
