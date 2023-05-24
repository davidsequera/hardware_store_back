package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.City;
import com.hardware.tools.persistence.CityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    public void testFindAllCities() {
        // Arrange
        City city1 = new City("1", "City1");
        City city2 = new City("2", "City2");
        Flux<City> cities = Flux.just(city1, city2);
        int expectedCount = Objects.requireNonNull(cities.count().block()).intValue();
        when(cityRepository.findAll()).thenReturn(cities);
        // Act
        Flux<City> result = cityService.findAllCities();

        // Assert
        assertNotNull(result);
        assertEquals(expectedCount, result.count().block());
    }

    @Test
    public void testFindCityById() {
        // Arrange
        String cityId = "1";
        City expectedCity = new City("1", "City1");
        when(cityRepository.findById(cityId)).thenReturn(Mono.just(expectedCity));

        // Act
        Mono<City> result = cityService.findCityById(cityId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedCity, result.block());
    }

    @Test
    public void testFindCitiesById() {
        // Arrange
        List<String> cityIds = Arrays.asList("1", "2");
        City city1 = new City("1", "City1");
        City city2 = new City("2", "City2");
        when(cityRepository.findAllByIds(cityIds)).thenReturn(Flux.just(city1, city2));

        // Act
        Flux<City> result = cityService.findCitiesById(cityIds);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }
}
