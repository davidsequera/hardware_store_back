package com.hardware.tools.presentation;

import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.entities.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class CityController {
    @Autowired
    private CityService cityService;
    @QueryMapping
    public Flux<City> getCities() {
        return cityService.findAllCities();
    }
    @QueryMapping
    public Mono<City> getCityById(@Argument String id) {
        return cityService.findCityById(id);
    }
}
