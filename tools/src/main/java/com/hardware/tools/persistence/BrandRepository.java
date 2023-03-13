package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Brand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BrandRepository extends ReactiveMongoRepository<Brand, String> {
}
