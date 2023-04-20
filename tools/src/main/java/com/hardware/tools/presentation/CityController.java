package com.hardware.tools.presentation;

import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This is a Spring Boot controller for handling GraphQL queries related to Cities.
 */
@Controller
public class CityController {

    @Autowired
    private CityService cityService;

    /**
     * This method handles the GraphQL query for fetching all Cities.
     *
     * @return A Flux of City objects representing all Cities in the system.
     */
    @QueryMapping
    public Flux<City> getCities() {
        return cityService.findAllCities();
    }

    /**
     * This method handles the GraphQL query for fetching a City by ID.
     *
     * @param id The ID of the City to fetch.
     * @return A Mono of City object representing the City with the given ID.
     */
    @QueryMapping
    public Mono<City> getCityById(@Argument String id) {
        return cityService.findCityById(id);
    }
}

