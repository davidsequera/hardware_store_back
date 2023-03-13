package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPageInput;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.hardware.tools.persistence.ToolRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.PublicKey;
import java.util.Arrays;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public Flux<Tool> findToolsByInput(ToolPageInput toolsInput) {
        // TODO: Implement the logic for fetching the tools based on the input criteria (e.g. filter by city, brand, etc.)
        return toolRepository.findAll();
    }

    public Flux<Tool> findAllTools() {
        return toolRepository.findAll();
    }

    public Mono<Tool> findToolById(String id) {
        Mono<Tool> tool = toolRepository.findById(id);
        return tool;
    }
    public Flux<Tool> findToolsByBrand(String id) {
        return toolRepository.findToolsByBrandId(new ObjectId(id));
    }

    public Mono<Tool> createTool(final Tool tool) {
        return toolRepository.save(tool);
    }

    public Mono<Tool> updateTool(final Tool tool){
        return toolRepository.findById(tool.getId()).map(t -> tool).flatMap(toolRepository::save);
    }

    public Mono<Tool> deleteTool(final String id){
        Mono<Tool> tool =  toolRepository.findById(id);
        toolRepository.deleteById(id).block();
        return tool;
    }
}
