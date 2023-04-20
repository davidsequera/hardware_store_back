package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Tool;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


/**
 * This is a Spring Data MongoDB repository interface for Tool objects.
 */
public interface ToolRepository extends ReactiveMongoRepository<Tool, String> {

    /**
     * This method returns a paged list of all Tool objects in the system.
     *
     * @param pageable The pageable object specifying the page size and number.
     * @return A Flux of Tool objects representing a paged list of all Tool objects in the system.
     */
    Flux<Tool> findAllBy(Pageable pageable);

    /**
     * This method returns a list of Tool objects whose name matches the given regular expression.
     *
     * @param expression The regular expression to match against the Tool name.
     * @return A Flux of Tool objects whose name matches the given regular expression.
     */
    @Query("{'name': {$regex: ?0 , $options: 'i'}})")
    Flux<Tool> findByQuery(String expression);

    /**
     * This method returns a list of Tool objects whose Brand ID matches the given ObjectId.
     *
     * @param brandId The ObjectId of the Brand to filter by.
     * @return A Flux of Tool objects whose Brand ID matches the given ObjectId.
     */
    Flux<Tool> findToolsByBrandId(String brandId);

}

