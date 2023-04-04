package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPage;
import com.hardware.tools.domain.entities.ToolPageInput;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import com.hardware.tools.persistence.ToolRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public Flux<Tool> findToolsByInput(ToolPageInput toolsInput) {
        // TODO: Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        Pageable pageable = PageRequest.of(toolsInput.page, toolsInput.size);
        return toolRepository.findAllBy(pageable);
    }

    public Flux<Tool> findAllTools() {
        return toolRepository.findAll();
    }

    public Mono<Tool> findToolById(String id) {
        return toolRepository.findById(id);
    }
    public Flux<Tool> findToolsByBrand(String id) {
        return toolRepository.findToolsByBrandId(new ObjectId(id));
    }

    public Mono<Tool> createTool(final Tool tool) {
        return toolRepository.save(tool);
    }

    public Mono<Tool> updateTool(final Tool tool){
        // TODO: Implement the logic for updating the tool
        return toolRepository.findById(tool.getId())
                .flatMap(existingUser -> {
                    if (tool.getName() != null && !tool.getName().isEmpty()) {
                        existingUser.setName(tool.getName());
                    }
                    if (tool.getDescription() != null && !tool.getDescription().isEmpty()) {
                        existingUser.setDescription(tool.getDescription());
                    }
                    if (tool.getBrandId() != null ) {
                        existingUser.setBrandId(tool.getBrandId().toString());
                    }
                    if (tool.getPrice() != 0 ) {
                        existingUser.setBrandId(tool.getBrandId().toString());
                    }
                    if(tool.getCities() != null){
                        existingUser.setCities(tool.getCities());
                    }
                    // Amount
                    return toolRepository.save(existingUser);
                });
    }

    public Mono<Tool> deleteTool(final String id){
        Mono<Tool> tool =  toolRepository.findById(id);
        toolRepository.deleteById(id).block();
        return tool;
    }
}
