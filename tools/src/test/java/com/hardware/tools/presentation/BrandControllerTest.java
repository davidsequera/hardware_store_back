package com.hardware.tools.presentation;

import com.hardware.tools.domain.BrandService;
import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.domain.entities.Tool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @Mock
    private ToolService toolService;

    @InjectMocks
    private BrandController brandController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBrands() {
        when(brandService.findAllBrands()).thenReturn(Flux.just(new Brand(), new Brand()));

        Flux<Brand> brands = brandController.getBrands();

        Assertions.assertNotNull(brands);
        Assertions.assertEquals(2, brands.count().block());
    }

    @Test
    void testGetBrandById() {
        when(brandService.findById(any())).thenReturn(Mono.just(new Brand()));

        Mono<Brand> brand = brandController.getBrandById("123");

        Assertions.assertNotNull(brand);
    }

    @Test
    void testTools() {
        Brand brand = new Brand();
        brand.setId("123");

        when(toolService.findToolsByBrandId(any())).thenReturn(Flux.just(new Tool(), new Tool()));

        Flux<Tool> tools = brandController.tools(brand);

        Assertions.assertNotNull(tools);
        Assertions.assertEquals(2, tools.count().block());
    }
}
