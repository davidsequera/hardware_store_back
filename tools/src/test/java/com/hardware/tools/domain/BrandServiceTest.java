package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.persistence.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandService brandService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllBrands() {
        // Create a sample list of brands
        Brand brand1 = new Brand("1", "Brand 1", "Description 1", "2020");
        Brand brand2 = new Brand("2", "Brand 2", "Description 2", "2015");
        Brand brand3 = new Brand("3", "Brand 3", "Description 3", "2000");
        Flux<Brand> brandFlux = Flux.just(brand1, brand2, brand3);
        int count = brandFlux.count().block().intValue();
        // Mock the brand repository to return the sample list of brands
        when(brandRepository.findAll()).thenReturn(brandFlux);

        // Call the method under test
        Flux<Brand> result = brandService.findAllBrands();

        // Assert
        assertNotNull(result);
        assertEquals(count, result.count().block());
    }

    @Test
    public void testFindById() {
        // Create a sample brand
        Brand brand = new Brand("1", "Brand 1", "Description 1", "2020");

        // Mock the brand repository to return the sample brand
        when(brandRepository.findById("1")).thenReturn(Mono.just(brand));

        // Call the method under test
        Mono<Brand> result = brandService.findById("1");

        assertNotNull(result);
        assertEquals(brand, result.block());
    }
}
