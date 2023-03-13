package com.hardware.tools.presentation;

import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.BrandService;
import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.domain.entities.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ToolService toolService;
    @Autowired
    private CityService cityService;
    @QueryMapping
    public Flux<Brand> getBrands() {
        // Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        // and return the list of tools
        return brandService.findAllBrands();
    }
    @QueryMapping
    public Mono<Brand> getBrandById(@Argument String id) {
        // Find the tool with the specified ID in the repository and return it
        return brandService.findById(id);
    }
    @SchemaMapping
    public Flux<Tool> tools(Brand brand) {
        // Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        // and return the list of tools
        return toolService.findToolsByBrand(brand.getId());
    }
}
