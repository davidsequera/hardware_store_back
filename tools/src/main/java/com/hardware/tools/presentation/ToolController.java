package com.hardware.tools.presentation;

import com.hardware.tools.domain.BrandService;
import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.inputs.ToolInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.hardware.tools.domain.entities.*;
import com.hardware.tools.domain.ToolService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Controller
public class ToolController {

    @Autowired
    private ToolService toolService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CityService cityService;
    @QueryMapping
    public Flux<Tool> getTools(ToolPageInput input) {
        // Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        // and return the list of tools
        return toolService.findToolsByInput(input);
    }
    @QueryMapping
    public Flux<Tool> getAllTools() {
        // Return all the tools in the repository
        return toolService.findAllTools();
    }
    @QueryMapping
    @PreAuthorize("isAuthenticated")
    public Mono<Tool> getToolById(@Argument String id) {
        // Find the tool with the specified ID in the repository and return it

        return toolService.findToolById(id);
    }

    @SchemaMapping
    public Flux<City> cities(Tool tool) {
        // Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        // and return the list of tools
        return cityService.findCitiesById(tool.getCities());
    }
    @SchemaMapping
    public Mono<Brand> brand(Tool tool) {
        // Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        // and return the list of tools
        return brandService.findById(tool.getBrandId().toString());
    }


    @MutationMapping
    public Mono<Tool> createTool(@Argument ToolInput input){

        // Add the tool to the repository and return it
        return toolService.createTool(input.toTool());
    }
    @MutationMapping
    public Mono<Tool> updateTool(@Argument ToolInput input) {
        // Update the tool in the repository and return it
        return toolService.updateTool(input.toTool());
    }
    @MutationMapping
    public Mono<Tool> deleteTool(@Argument String id) {
        // Delete the tool from the repository and return it
        return toolService.deleteTool(id);
    }
}
