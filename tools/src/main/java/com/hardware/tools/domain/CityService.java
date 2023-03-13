package com.hardware.tools.domain;


import com.hardware.tools.domain.entities.City;
import com.hardware.tools.persistence.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public Flux<City> findAllCities() {
        return cityRepository.findAll();
    }

    public Mono<City> findCityById(String id) {
        return cityRepository.findById(id);
    }

    public Flux<City> findCitiesById(List<String> cities) {
        return cityRepository.findAllByIds(cities);
    }
}
