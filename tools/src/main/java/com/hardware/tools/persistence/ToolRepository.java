package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Tool;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ToolRepository extends ReactiveMongoRepository<Tool, String> {

//    TODO : UPDATE ONE
    // TODO: Paged query
    Flux<Tool> findToolsByBrandId(ObjectId brandId);
}
