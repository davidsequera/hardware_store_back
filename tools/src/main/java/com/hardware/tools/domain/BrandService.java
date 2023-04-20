package com.hardware.tools.domain;

import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.persistence.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BrandService {

    // Injecting BrandRepository using Spring's dependency injection
    @Autowired
    private BrandRepository brandRepository;

    /**
     * Fetches all brands from the database
     *
     * @return a Flux stream of Brand objects
     */
    public Flux<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * Fetches a brand by its ID from the database
     *
     * @param brand_id the ID of the brand to fetch
     * @return a Mono stream of the Brand object
     */
    public Mono<Brand> findById(String brand_id) {
        return brandRepository.findById(brand_id);
    }
}
