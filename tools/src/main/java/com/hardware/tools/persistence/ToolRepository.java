package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Tool;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


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
    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Flux<Tool> testInjection(String name, Pageable pageable);

    /**
     * This method returns a list of Tool objects whose name matches the given regular expression.
     *
     * @param query The regular expression to match against the Tool name.
     * @return A Flux of Tool objects whose name matches the given regular expression.
     */
    @CountQuery("?0")
    Mono<Long> countByQuery(String query);

    // TODO: Injection is posible so we need to sanitize the input
    @Query("?0")
    Flux<Tool> findByFilter(String query, Pageable pageable);

//    @Query("{ ?0: {$regex: ?1 , $options: 'i'}})")
//    Mono<Long> countByFilter(String field, String expression, Pageable pageable);

    /**
     * This method returns a list of Tool objects whose Brand ID matches the given ObjectId.
     *
     * @param brand_id The ObjectId of the Brand to filter by.
     * @return A Flux of Tool objects whose Brand ID matches the given ObjectId.
     */
    @Query("{ 'brand_id': ?0 }")
    Flux<Tool> findToolsByBrand_id(String brand_id);
}


