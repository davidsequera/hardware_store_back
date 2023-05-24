package com.hardware.tools.presentation;

import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Controller
@SchemaMapping(typeName = "ToolPage")
public class ToolPageController {


    @Autowired
    private ToolService toolService;


    @SchemaMapping
    public Flux<Tool> tools(ToolPage toolPage) {
        return toolService.findFilteredToolsByInput(toolPage.getInput(), toolPage.getFilter());
    }
//    @SchemaMapping
//    public Mono<Long> length(ToolPage toolPage) {
//        return toolService.countToolsByFilter(toolPage.getInput(), toolPage.getFilter());
//    }
//    @SchemaMapping
//    public Mono<Long> page(ToolPage toolPage, @Argument ToolPageInput input) {
//        return toolService.findToolsByInput(input).count();
//    }
    @SchemaMapping
    public Mono<Long> total_tools() {
        return toolService.countTools();
    }

//    @SchemaMapping
//    public Mono<Long> page_length(ToolPage toolPage, @Argument ToolPageInput input) {
//        return toolService.findToolsByInput(input).count();
//    }

    @SchemaMapping
    public Mono<Long> total_pages(ToolPage toolPage) {
        return toolService.countTools().map(total_tools -> total_tools / toolPage.getInput().size);
    }
}
