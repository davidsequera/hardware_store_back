package com.hardware.tools.persistence;

import com.hardware.tools.domain.entities.Brand;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * This is a Spring Data MongoDB repository interface for Brand objects.
 */
public interface BrandRepository extends ReactiveMongoRepository<Brand, String> {
}

