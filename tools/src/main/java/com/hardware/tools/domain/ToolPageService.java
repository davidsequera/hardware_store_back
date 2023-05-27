package com.hardware.tools.domain;


import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import com.hardware.tools.persistence.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ToolPageService {

    @Autowired
    private ToolRepository toolRepository;


    @Autowired
    private FilterComponent filterComponent;
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

    /**
     *
     * Returns a Flux of Tool objects that match the provided search string.
     *
     * @param search the search string to match against the tool names
     * @return a Flux of Tool objects that match the provided search string
     */

    public Flux<Tool> findToolsByName(ToolPageInput toolInput, String search ) {
        Pageable pageable = PageRequest.of(toolInput.page, toolInput.size);
        return toolRepository.testInjection(search, pageable);
    }

    public Flux<Tool> filterTools(ToolPageInput input, String query) {
        Pageable pageable =PageRequest.of(input.page, input.size);
        if(input.sort_name != null && input.sort_direction != null) {
            pageable = PageRequest.of(input.page, input.size, Sort.Direction.fromString(input.sort_direction), input.sort_name);
        }
        System.out.println(input);
        System.out.println(query);
        return toolRepository.findByFilter(query, pageable);
    }


    public Mono<Long> countToolsPage(ToolPageInput input, String query) {
        Pageable pageable =PageRequest.of(input.page, input.size);
        if(input.sort_name != null && input.sort_direction != null) {
            pageable = PageRequest.of(input.page, input.size, Sort.Direction.fromString(input.sort_direction), input.sort_name);
        }
        return toolRepository.findByFilter(query, pageable).count();
    }
    public Mono<Long> countToolsByFilter(String query) {
        return toolRepository.countByQuery(query);
    }

}
