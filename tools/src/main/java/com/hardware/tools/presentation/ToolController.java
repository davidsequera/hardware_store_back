package com.hardware.tools.presentation;

import com.hardware.tools.domain.BrandService;
import com.hardware.tools.domain.CityService;
import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.*;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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

    /**
     * Query method to retrieve a list of tools based on a given input, such as a page number and size.
     * @param input the ToolPageInput containing the page number and size
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Flux<Tool> getTools(@Argument ToolPageInput input) {
        return toolService.findToolsByInput(input);
    }


    /**
     * Query method to retrieve a list of tools based on a given input, such as a page number and size.
     * Also filters by brand and city.
     * @param input the ToolPageInput containing the page number and size
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Mono<ToolPage> getFilteredTools(@Argument ToolPageInput input, ArgumentValue<FilterInput> filter ) {
        var toolPage = new ToolPage(input, filter.value());
        return Mono.just(toolPage);
    }

    /**
     * Query method to retrieve the total count of tools.
     * @return a Mono of the total count of tools
     */
    @QueryMapping
    public Mono<Long> countTools() {
        return toolService.countTools();
    }

    /**
     * Query method to retrieve a list of tools by name, based on a given input and search term.
     * @param input the ToolPageInput containing the page number and size
     * @param search the search term for the tool name
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Flux<Tool> getToolsByName(@Argument ToolPageInput input, @Argument String search) {
        return toolService.findToolsByName(search);
    }

    /**
     * Query method to retrieve all tools.
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Flux<Tool> getAllTools() {
        return toolService.findAllTools();
    }

    /**
     * Query method to retrieve a tool by ID.
     * @param id the ID of the tool to retrieve
     * @return a Mono of the Tool object
     */
    @QueryMapping
//  TODO: Uncomment the following line to enable authentication for this method.
    @PreAuthorize("isAuthenticated")
    public Mono<Tool> getToolById(@Argument String id) {
        return toolService.findToolById(id);
    }

    /**
     * Schema mapping method to retrieve a Flux of cities for a given Tool object.
     * @param tool the Tool object
     * @return a Flux of City objects
     */
    @SchemaMapping
    public Flux<City> cities(Tool tool) {
        return cityService.findCitiesById(tool.getCities());
    }

    /**
     * Schema mapping method to retrieve a Mono of a Brand object for a given Tool object.
     * @param tool the Tool object
     * @return a Mono of a Brand object
     */
    @SchemaMapping
    public Mono<Brand> brand(Tool tool) {
        return brandService.findById(tool.getBrand_id());
    }

    /**
     * Mutation method to create a new Tool object.
     * @param input the ToolInput containing the data for the new Tool
     * @return a Mono of the created Tool object
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<Tool> createTool(@Argument ToolInput input){

        return toolService.createTool(input.toTool());
    }
    /**
     * Mutation to update an existing tool.
     *
     * @param input the input parameters for updating the tool.
     * @return a Mono that emits the updated tool.
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<Tool> updateTool(@Argument ToolInput input) {
        return toolService.updateTool(input.toTool());
    }

    /**
     * Mutation to delete a tool.
     *
     * @param id the id of the tool to delete.
     * @return a Mono that completes when the tool has been deleted.
     */
    @MutationMapping
    @PreAuthorize("isAuthenticated")
    public Mono<Tool> deleteTool(@Argument String id) {
        return toolService.deleteTool(id);
    }


}
