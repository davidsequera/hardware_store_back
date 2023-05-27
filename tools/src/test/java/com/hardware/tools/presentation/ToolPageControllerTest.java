package com.hardware.tools.presentation;

import com.hardware.tools.domain.ToolPageService;
import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.entities.ToolPage;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.graphql.data.ArgumentValue;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ToolPageControllerTest {
    @Mock
    private ToolPageService toolPageService;

    @InjectMocks
    private ToolPageController toolPageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetTools() {
        when(toolPageService.findToolsByInput(any())).thenReturn(Flux.just(new Tool(), new Tool()));

        Flux<Tool> tools = toolPageController.getTools(new ToolPageInput(0,10,"name", "ASC"));

        Assertions.assertNotNull(tools);
        Assertions.assertEquals(2, tools.count().block());
    }

    @Test
    void testGetToolsByName() {
        when(toolPageService.findToolsByName(any(), anyString())).thenReturn(Flux.just(new Tool(), new Tool()));

        Flux<Tool> tools = toolPageController.getToolsByName(new ToolPageInput(0,10,"name", "ASC"), "search term");

        Assertions.assertNotNull(tools);
        Assertions.assertEquals(2, tools.count().block());
    }
    @Test
    void testgetToolsByFilter() {
        when(toolPageService.findToolsByInput(any())).thenReturn(Flux.just(new Tool(), new Tool()));

        Mono<ToolPage> toolPage = toolPageController.getToolsByFilter(new ToolPageInput(0,10,"name","ASC"), ArgumentValue.ofNullable(List.of(new FilterInput("name", List.of("test")))));

        Assertions.assertNotNull(toolPage);
    }
}
