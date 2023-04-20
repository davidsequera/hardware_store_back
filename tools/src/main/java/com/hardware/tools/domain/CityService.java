package com.hardware.tools.domain;


import com.hardware.tools.domain.entities.City;
import com.hardware.tools.persistence.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Provides methods for accessing and manipulating city data in the database.
 */
@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    /**
     * Returns a Flux of all cities in the database.
     *
     * @return a Flux of all cities
     */
    public Flux<City> findAllCities() {
        return cityRepository.findAll();
    }

    /**
     * Returns a Mono of the city with the given ID.
     *
     * @param id the ID of the city to retrieve
     * @return a Mono of the city with the given ID
     */
    public Mono<City> findCityById(String id) {
        return cityRepository.findById(id);
    }

    /**
     * Returns a Flux of the cities with the given IDs.
     *
     * @param cities a list of IDs of the cities to retrieve
     * @return a Flux of the cities with the given IDs
     */
    public Flux<City> findCitiesById(List<String> cities) {
        return cityRepository.findAllByIds(cities);
    }
}
