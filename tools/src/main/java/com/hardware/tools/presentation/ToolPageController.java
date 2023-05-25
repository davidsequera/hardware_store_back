package com.hardware.tools.presentation;

import com.hardware.tools.domain.ToolService;
import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPage;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.ArgumentValue;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Controller
@SchemaMapping(typeName = "ToolPage")
public class ToolPageController {


    @Autowired
    private ToolService toolService;


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


    @SchemaMapping
    public Flux<Tool> tools(ToolPage toolPage) {
        return toolService.findFilteredToolsByInput(toolPage.getInput(), toolPage.getFilter());
    }
    @SchemaMapping
    public Mono<Long> length(ToolPage toolPage) {
        return toolService.countToolsByFilter(toolPage.getInput(), toolPage.getFilter());
    }
    @SchemaMapping
    public Mono<Long> page(ToolPage toolPage) {
        return toolService.countToolsByFilter(toolPage.getInput(), toolPage.getFilter() ).map(length -> length / toolPage.getInput().size).map(
                totalPages -> {
                    if (toolPage.getInput().page > (totalPages-1)) {
                        return totalPages;
                    } else {
                        return Long.parseLong(String.valueOf(toolPage.getInput().page));
                    }
                }
        );
    }
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
        return toolService.countToolsByFilter(toolPage.getInput(), toolPage.getFilter() ).map(length -> length / toolPage.getInput().size);
    }
}
