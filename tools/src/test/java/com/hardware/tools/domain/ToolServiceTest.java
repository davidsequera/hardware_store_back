package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Tool;
import com.hardware.tools.domain.inputs.FilterInput;
import com.hardware.tools.domain.inputs.ToolPageInput;
import com.hardware.tools.persistence.ToolRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToolServiceTest {

    @Mock
    private ToolRepository toolRepository;

    @InjectMocks
    private ToolService toolService;


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
    public void testFindFilteredToolsByInput() {
        // Arrange
        int  page = 0;
        int  size = 10;
        ToolPageInput input = new ToolPageInput(page, size, "id", "ASC");
        FilterInput filter = new FilterInput("name", "hammer");

        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
//        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.findByFilter(anyString(), anyString(), any())).thenReturn(Flux.just(tool1));

        // Act
        Flux<Tool> result = toolService.findFilteredToolsByInput(input, filter);

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
        FilterInput filter = new FilterInput("name", "hammer");

        when(toolRepository.findByFilter(anyString(), anyString(), any())).thenReturn(Flux.empty());

        // Act
        Mono<Long> result = toolService.countToolsByFilter(input, filter);

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
        when(toolRepository.findByQuery("name",search)).thenReturn(Flux.just(tool1, tool2));

        // Act
        Flux<Tool> result = toolService.findToolsByName(search);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }


    @Test
    public void testFindAllTools() {
        // Arrange
        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.findAll()).thenReturn(Flux.just(tool1, tool2));

        // Act
        Flux<Tool> result = toolService.findAllTools();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }


    @Test
    public void testFindToolById() {
        // Arrange
        String toolId = "1";
        Tool expectedTool = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        when(toolRepository.findById(toolId)).thenReturn(Mono.just(expectedTool));

        // Act
        Mono<Tool> result = toolService.findToolById(toolId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedTool, result.block());
    }

    @Test
    public void testFindToolsByBrandId() {
        // Arrange
        String brandId = "1";
        Tool tool1 = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        Tool tool2 = new Tool("2", "Circular Saw", "A Circular Saw", "23456", 9.99, 20, Arrays.asList("New York", "London"));
        when(toolRepository.findToolsByBrand_id(brandId)).thenReturn(Flux.just(tool1, tool2));

        // Act
        Flux<Tool> result = toolService.findToolsByBrandId(brandId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.count().block());
    }


    @Test
    public void testCountTools() {
        // Arrange
        when(toolRepository.count()).thenReturn(Mono.just(10L));

        // Act
        Mono<Long> result = toolService.countTools();

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.block());
    }


    @Test
    public void testCreateTool() {
        // Arrange
        Tool tool = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        when(toolRepository.save(tool)).thenReturn(Mono.just(tool));

        // Act
        Mono<Tool> result = toolService.createTool(tool);

        // Assert
        assertNotNull(result);
        assertEquals(tool, result.block());
    }


    @Test
    public void testUpdateTool() {
        // Arrange
        Tool tool = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        when(toolRepository.findById(tool.getId())).thenReturn(Mono.just(tool));
        when(toolRepository.save(tool)).thenReturn(Mono.just(tool));

        // Act
        Mono<Tool> result = toolService.updateTool(tool);

        // Assert
        assertNotNull(result);
        assertEquals(tool, result.block());
    }


    @Test
    public void testDeleteTool() {
        // Arrange
        String toolId = "1";
        Tool tool = new Tool("1", "Hammer", "A heavy-duty hammer", "123456", 19.99, 10, Arrays.asList("New York", "London"));
        when(toolRepository.findById(toolId)).thenReturn(Mono.just(tool));
        when(toolRepository.deleteById(toolId)).thenReturn(Mono.empty());

        // Act
        Mono<Tool> result = toolService.deleteTool(toolId);

        // Assert
        assertNotNull(result);
        assertEquals(tool, result.block());
        verify(toolRepository, times(1)).deleteById(toolId);
    }
}

