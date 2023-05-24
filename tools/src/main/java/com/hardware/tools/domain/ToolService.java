package com.hardware.tools.domain;

import com.hardware.tools.domain.exceptions.GraphQLToolsException;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.inputs.ToolPageInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.hardware.tools.persistence.ToolRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


/**
 * This class contains the business logic for interacting with the ToolRepository and provides
 * various methods for performing CRUD operations on the tools.
 */
@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    /**
     * Returns a Flux of Tool objects based on the input criteria provided in the ToolPageInput object.
     *
     * @param toolsInput the input criteria for fetching the tools
     * @return a Flux of Tool objects based on the input criteria
     */
    public Flux<Tool> findToolsByInput(ToolPageInput toolsInput) {
        Pageable pageable = PageRequest.of(toolsInput.page, toolsInput.size);
        return toolRepository.findAllBy(pageable);
    }
    public Flux<Tool> findFilteredToolsByInput(ToolPageInput input, FilterInput filter) {
        Pageable pageable =PageRequest.of(input.page, input.size);
        pageable = PageRequest.of(input.page, input.size, Sort.Direction.fromString(input.sort_direction), input.sort_name);
        if(input.sort_name != null && input.sort_direction != null) {
        }
        System.out.println(input);
        System.out.println(filter);

        // Sorting?
//        if(input.sort_name != null &&   Arrays.stream(Tool.class.getDeclaredFields()).map(Field::getName).noneMatch(input.sort_name::equals)){
//            throw new GraphQLToolsException("Invalid sort field: " + input.sort_name);
//        }
//
//        if(Arrays.stream(Tool.class.getDeclaredFields()).map(Field::getName).noneMatch(filter.tool_field::equals)){
//            throw new GraphQLToolsException("Invalid filter field: " + filter.tool_field);
//        }
//
//        if(filter.tool_value == null) {
//            return toolRepository.findAllBy(pageable);
//        }

        return toolRepository.findByFilter(filter.tool_field, filter.tool_value, pageable);
    }


    public Mono<Long> countToolsByFilter(ToolPageInput input, FilterInput filter) {
        Pageable pageable = PageRequest.of(input.page, input.size, Sort.Direction.fromString(input.sort_direction), input.sort_name);
        return toolRepository.findByFilter(filter.tool_field, filter.tool_value, pageable).count();
    }



    /**
     *
     * Returns a Flux of Tool objects that match the provided search string.
     *
     * @param search the search string to match against the tool names
     * @return a Flux of Tool objects that match the provided search string
     */
    public Flux<Tool> findToolsByName(String search) {
        return toolRepository.findByQuery(search);
    }

    /**
     * Returns a Flux of all Tool objects in the repository.
     *
     * @return a Flux of all Tool objects in the repository
     */
    public Flux<Tool> findAllTools() {
        return toolRepository.findAll();
    }

    /**
     * Returns a Mono of the Tool object with the provided id.
     *
     * @param id the id of the tool to fetch
     * @return a Mono of the Tool object with the provided id
     */
    public Mono<Tool> findToolById(String id) {
        return toolRepository.findById(id);
    }

    /**
     * Returns a Flux of Tool objects that belong to the brand with the provided id.
     *
     * @param id the id of the brand to fetch tools for
     * @return a Flux of Tool objects that belong to the brand with the provided id
     */
    public Flux<Tool> findToolsByBrandId(String id) {
        return toolRepository.findToolsByBrand_id(id);
    }

    /**
     * Returns a Mono of the count of Tool objects in the repository.
     *
     * @return a Mono of the count of Tool objects in the repository
     */
    public Mono<Long> countTools() {
        return toolRepository.count();
    }

    /**
     * Saves the provided Tool object in the repository and returns a Mono of the saved Tool object.
     *
     * @param tool the Tool object to save in the repository
     * @return a Mono of the saved Tool object
     */
    public Mono<Tool> createTool(final Tool tool) {
        return toolRepository.save(tool);
    }

    /**
     * Updates the Tool object with the provided id based on the fields provided in the input Tool object
     * and returns a Mono of the updated Tool object.
     *
     * @param tool the Tool object with the updated fields
     * @return a Mono of the updated Tool object
     */
    public Mono<Tool> updateTool(final Tool tool){
        // TODO: Implement the logic for updating the tool
        return toolRepository.findById(tool.getId())
                .flatMap(existingTool -> {
                    if (tool.getName() != null && !tool.getName().isEmpty()) {
                        existingTool.setName(tool.getName());
                    }
                    if (tool.getDescription() != null && !tool.getDescription().isEmpty()) {
                        existingTool.setDescription(tool.getDescription());
                    }
                    if (tool.getBrand_id() != null ) {
                        existingTool.setBrand_id(tool.getBrand_id());
                    }
                    if (tool.getPrice() != 0 ) {
                        existingTool.setPrice(tool.getPrice());
                    }
                    if(tool.getCities() != null && !tool.getCities().isEmpty()){
                        existingTool.setCities(tool.getCities());
                    }
                    // Amount
                    return toolRepository.save(existingTool);
                });
    }

    /**
     * Deletes a tool with the given ID from the database and returns a Mono of the deleted tool.
     *
     * @param id the ID of the tool to delete
     * @return a Mono of the deleted tool
     */
    public Mono<Tool> deleteTool(final String id){
        // Find the tool by ID and save it in a Mono
        Mono<Tool> tool =  toolRepository.findById(id);
        // Delete the tool from the database using the repository
        toolRepository.deleteById(id).subscribeOn(Schedulers.boundedElastic()).subscribe();
        // Return the Mono of the deleted tool
        return tool;
    }

}
