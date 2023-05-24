package com.hardware.tools.presentation;

import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.entities.City;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;


    private ArrayList<City> cities;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCities() {
        when(cityService.findAllCities()).thenReturn(Flux.just(new City("1", "a"), new City("1", "b")));

        Flux<City> cities = cityController.getCities();

        Assertions.assertNotNull(cities);
        Assertions.assertEquals(2, cities.count().block());
    }

    @Test
    void testGetCityById() {
        when(cityService.findCityById(any())).thenReturn(Mono.just(new City("1", "a")));

        Mono<City> city = cityController.getCityById("123");

        Assertions.assertNotNull(city);
    }
}
