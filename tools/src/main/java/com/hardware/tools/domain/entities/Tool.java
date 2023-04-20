package com.hardware.tools.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Document(collection = "tools")
public class Tool {
    @Id
    @Getter @Setter
    private String id;
    @Getter @Setter private String name;
    @Getter @Setter private String description;
    @Field(value ="brand" ,targetType = FieldType.OBJECT_ID)
    @Getter @Setter private String brandId;
    @Getter @Setter private double price;
    @Getter @Setter private int amount;
    @Field(targetType = FieldType.OBJECT_ID)
    @Getter @Setter private List<String> cities;

    // Constructors
    public Tool(String id, String name, String description, String brandId, double price, int amount, List<String>  cities) {
        this.id = id;
        this.name = name;
        this.description = description;
//        TODO: Fix update tool
        this.brandId = brandId;
        this.price = price;
        this.amount = amount;
        this.cities = cities != null? cities : new ArrayList<>();
    }
    public Tool(String name, String description, String brandId, double price, int amount, List<String>  cities) {
        this.name = name;
        this.description = description;
        this.brandId = brandId;
        this.price = price;
        this.amount = amount;
        this.cities = cities != null? cities : new ArrayList<>();
    }


    // toString method
    @Override
    public String toString() {
        return "Tool{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brandId='" + brandId + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", cities='" + cities + '\'' +
                '}';
    }
}
