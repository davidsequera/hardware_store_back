package com.hardware.tools.presentation;

import com.hardware.tools.domain.FilterComponent;
import com.hardware.tools.domain.ToolPageService;
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

import java.util.List;

@Controller
@SchemaMapping(typeName = "ToolPage")
public class ToolPageController {


    @Autowired
    private ToolPageService toolPageService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private FilterComponent filterComponent;

    /**
     * Query method to retrieve a list of tools based on a given input, such as a page number and size.
     * @param input the ToolPageInput containing the page number and size
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Flux<Tool> getTools(@Argument ToolPageInput input) {
        return toolPageService.findToolsByInput(input);
    }

    /**
     * Query method to retrieve a list of tools by name, based on a given input and search term.
     * @param input the ToolPageInput containing the page number and size
     * @param search the search term for the tool name
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Flux<Tool> getToolsByName(@Argument ToolPageInput input, @Argument String search) {
        return toolPageService.findToolsByName(input,search);
    }


    /**
     * Query method to retrieve a list of tools based on a given input, such as a page number and size.
     * Also filters by brand and city.
     * @param input the ToolPageInput containing the page number and size
     * @return a Flux of Tool objects
     */
    @QueryMapping
    public Mono<ToolPage> getToolsByFilter(@Argument ToolPageInput input, ArgumentValue<List<FilterInput>> filters ) {
        var toolPage = new ToolPage(input, filters.value());
        if(filters.isPresent()) {
            assert filters.value() != null;
            if (!filters.value().isEmpty()) {
                toolPage.setQuery(filterComponent.filter(toolPage.getFilters(), Tool.class));
            }
        }

        return Mono.just(toolPage);
    }


    @SchemaMapping
    public Flux<Tool> tools(ToolPage toolPage) {
        return toolPageService.filterTools(toolPage.getInput(), toolPage.getQuery());
    }
    @SchemaMapping
    public Mono<Long> length(ToolPage toolPage) {
        return toolPageService.countToolsPage(toolPage.getInput(), toolPage.getQuery());
    }
    @SchemaMapping
    public Mono<Long> page(ToolPage toolPage) {
        return toolPageService.countToolsByFilter(toolPage.getQuery() ).map(length -> length / toolPage.getInput().size).map(
                totalPages -> {
                    if (toolPage.getInput().page > (totalPages)) {
                        return totalPages;
                    } else {
                        return Long.parseLong(String.valueOf(toolPage.getInput().page));
                    }
                }
        );
    }
    @SchemaMapping
    public Mono<Long> total(ToolPage toolPage) {
        return toolPageService.countToolsByFilter(toolPage.getQuery() );
    }

    @SchemaMapping
    public Mono<Long> pages(ToolPage toolPage) {
        return toolPageService.countToolsByFilter(toolPage.getQuery()).map(length -> (long) Math.ceil((double) length / toolPage.getInput().size));
    }
}
