package com.hardware.tools.domain.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
public class City {
    @Id
    public String id;
    public String name;

    public City(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
