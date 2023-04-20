package com.hardware.tools.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a brand entity in the database.
 */
@NoArgsConstructor
@Document(collection = "brands")
public class Brand {
    // Fields
    @Id
    @Getter @Setter private String id; // The unique identifier of the brand.
    @Getter @Setter private String name; // The name of the brand.
    @Getter @Setter private String description; // A short description of the brand.
    @Getter @Setter private String foundation_year; // The year the brand was founded.

    // Constructors
    /**
     * Constructs a new Brand object with the specified parameters.
     *
     * @param id The unique identifier of the brand.
     * @param name The name of the brand.
     * @param description A short description of the brand.
     * @param foundation_year The year the brand was founded.
     */
    public Brand(String id, String name, String description, String foundation_year) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.foundation_year = foundation_year;
    }
}
