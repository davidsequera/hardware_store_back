package com.hardware.tools.presentation;

import com.hardware.tools.domain.BrandService;
import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.domain.entities.City;
import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPage;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.graphql.data.ArgumentValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ToolControllerTest {

    @Mock
    private ToolService toolService;

    @Mock
    private BrandService brandService;

    @Mock
    private CityService cityService;

    @InjectMocks
    private ToolController toolController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }





    @Test
    void testCountTools() {
        when(toolService.countTools()).thenReturn(Mono.just(10L));

        Mono<Long> count = toolController.countTools();

        Assertions.assertNotNull(count);
        Assertions.assertEquals(10L, count.block());
    }



    @Test
    void testGetAllTools() {
        when(toolService.findAllTools()).thenReturn(Flux.just(new Tool(), new Tool()));

        Flux<Tool> tools = toolController.getAllTools();

        Assertions.assertNotNull(tools);
        Assertions.assertEquals(2, tools.count().block());
    }

    @Test
    void testGetToolById() {
        when(toolService.findToolById(any())).thenReturn(Mono.just(new Tool()));

        Mono<Tool> tool = toolController.getToolById("123");

        Assertions.assertNotNull(tool);
    }

    @Test
    void testCities() {
        Tool tool = new Tool();
        tool.setCities(List.of("city1", "city2"));

        when(cityService.findCitiesById(any())).thenReturn(Flux.just(new City("1", "A"), new City("2", "B")));

        Flux<City> cities = toolController.cities(tool);

        Assertions.assertNotNull(cities);
        Assertions.assertEquals(2, cities.count().block());
    }

    @Test
    void testBrand() {
        Tool tool = new Tool();
        tool.setBrand_id("brand1");

        when(brandService.findById(any())).thenReturn(Mono.just(new Brand()));

        Mono<Brand> brand = toolController.brand(tool);

        Assertions.assertNotNull(brand);
    }

    @Test
    void testCreateTool() {
        ToolInput toolInput = new ToolInput("1","","","",1.0,1,List.of());

        when(toolService.createTool(any())).thenReturn(Mono.just(new Tool()));

        Mono<Tool> tool = toolController.createTool(toolInput);

        Assertions.assertNotNull(tool);
    }

    @Test
    void testUpdateTool() {
        ToolInput toolInput = new ToolInput("1","","","",1.0,1,List.of());

        when(toolService.updateTool(any())).thenReturn(Mono.just(new Tool()));

        Mono<Tool> tool = toolController.updateTool(toolInput);

        Assertions.assertNotNull(tool);
    }

    @Test
    void testDeleteTool() {
        when(toolService.deleteTool(any())).thenReturn(Mono.just(new Tool()));

        Mono<Tool> tool = toolController.deleteTool("123");

        Assertions.assertNotNull(tool);
    }
}
