package com.hardware.tools.presentation;

import com.hardware.tools.domain.ToolService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ToolPageControllerTest {
    @Mock
    private ToolService toolService;

    @InjectMocks
    private ToolPageController toolPageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetFilteredTools() {
        when(toolService.findToolsByInput(any())).thenReturn(Flux.just(new Tool(), new Tool()));

        Mono<ToolPage> toolPage = toolPageController.getFilteredTools(new ToolPageInput(0,10,"name","ASC"), ArgumentValue.ofNullable(new FilterInput("name", "value")));

        Assertions.assertNotNull(toolPage);
    }
}
