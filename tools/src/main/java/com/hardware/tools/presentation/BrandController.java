package com.hardware.tools.presentation;

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

/**
 * This is a Spring Boot controller for handling GraphQL queries related to Brands.
 */
@Controller
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ToolService toolService;

    /**
     * This method handles the GraphQL query for fetching all Brands.
     *
     * @return A Flux of Brand objects representing all Brands in the system.
     */
    @QueryMapping
    public Flux<Brand> getBrands() {
        return brandService.findAllBrands();
    }

    /**
     * This method handles the GraphQL query for fetching a Brand by ID.
     *
     * @param id The ID of the Brand to fetch.
     * @return A Mono of Brand object representing the Brand with the given ID.
     */
    @QueryMapping
    public Mono<Brand> getBrandById(@Argument String id) {
        return brandService.findById(id);
    }

    /**
     * This method handles the GraphQL schema mapping for fetching all Tools for a given Brand.
     *
     * @param brand The Brand for which to fetch Tools.
     * @return A Flux of Tool objects representing all Tools for the given Brand.
     */
    @SchemaMapping
    public Flux<Tool> tools(Brand brand) {
        return toolService.findToolsByBrand(brand.getId());
    }
}
