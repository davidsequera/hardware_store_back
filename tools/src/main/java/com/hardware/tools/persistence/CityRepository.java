package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.City;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * This is a Spring Data MongoDB repository interface for City objects.
 */
public interface CityRepository extends ReactiveMongoRepository<City, String> {

    /**
     * This method returns a Flux of all City objects whose IDs match the given list of IDs.
     *
     * @param ids The list of IDs to filter by.
     * @return A Flux of City objects whose IDs match the given list of IDs.
     */
    @Query("{'_id': { $in: ?0 }}")
    Flux<City> findAllByIds(List<String> ids);
}
