package com.hardware.tools.domain;


import com.hardware.tools.domain.entities.Brand;
import com.hardware.tools.persistence.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public Flux<Brand> findAllBrands() {
        return brandRepository.findAll();
    }

    public Mono<Brand> findById(String brand_id) {
        return brandRepository.findById(brand_id);
    }
}
