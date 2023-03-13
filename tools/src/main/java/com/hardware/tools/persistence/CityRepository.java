package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.City;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface CityRepository extends ReactiveMongoRepository<City, String> {

    @Query("{'_id': { $in: ?0 }}")
    Flux<City> findAllByIds(List<String> ids);
}
