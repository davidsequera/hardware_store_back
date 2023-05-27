package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import com.hardware.tools.persistence.ToolRepository;
import com.hardware.tools.domain.FilterComponent;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ToolPageServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolPageService toolService;


    @Mock
    private FilterComponent filterComponent;

    @Test
    public void testFindToolsByInput() {
        // Arrange
        int  page = 0;
        int size = 10;
        ToolPageInput toolsInput = new ToolPageInput(page, size, "id", "ASC");
        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.findAllBy(any())).thenReturn(Flux.just(tool1, tool2));

        // Act
        Flux<Tool> result = toolService.findToolsByInput(toolsInput);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }


    @Test
    public void testfilterTools() {
        // Arrange
        int  page = 0;
        int  size = 10;
        ToolPageInput input = new ToolPageInput(page, size, "id", "ASC");
        List<FilterInput> filters = List.of(new FilterInput("name", Arrays.asList("Hammer")));
        String query = "";

        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
//        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.findByFilter(anyString(), any())).thenReturn(Flux.just(tool1));

        // Act
        Flux<Tool> result = toolService.filterTools(input, query);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.count().block());
    }


    @Test
    public void testCountToolsByFilter() {
        // Arrange
        int page = 0;
        int size = 10;
        ToolPageInput input = new ToolPageInput(page, size, "id", "ASC");
        List<FilterInput> filters = List.of(new FilterInput("name", Arrays.asList("Hammer")));
        String query = "";

        when(toolRepository.findByFilter(anyString(), any())).thenReturn(Flux.empty());

        // Act
        Mono<Long> result = toolService.countToolsByFilter(query);

        // Assert
        assertNotNull(result);
        assertEquals(0L, result.block());
    }


    @Test
    public void testFindToolsByName() {
        // Arrange
        String search = "Tool";
        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.testInjection(search, any())).thenReturn(Flux.just(tool1, tool2));
        ToolPageInput input = mock(ToolPageInput.class);
        // Act
        Flux<Tool> result = toolService.findToolsByName(input, search );

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }
}
