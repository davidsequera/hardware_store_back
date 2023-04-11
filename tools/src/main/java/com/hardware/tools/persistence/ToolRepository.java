package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Tool;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface ToolRepository extends ReactiveMongoRepository<Tool, String> {

//    TODO : UPDATE ONE
    // TODO: Paged query
    Flux<Tool> findAllBy(Pageable pageable);
    Flux<Tool> findToolsByBrandId(ObjectId brandId);

}
