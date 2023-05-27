package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import org.springframework.beans.factory.annotation.Autowired;
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
