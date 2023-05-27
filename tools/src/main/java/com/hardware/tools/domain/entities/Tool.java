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
@Getter @Setter
public class Tool {
    @Id
    private String id;
    private String name;
    private String description;
    @Field(targetType = FieldType.OBJECT_ID)
    private String brand_id;
    private double price;
    private int amount;
    @Field(targetType = FieldType.OBJECT_ID)
    private List<String> cities;

    // Constructors
    public Tool(String id, String name, String description, String brand_id, double price, int amount, List<String>  cities) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.brand_id = brand_id;
        this.price = price;
        this.amount = amount;
        this.cities = cities != null? cities : new ArrayList<>();
    }


    public Tool(String name, String description, String brand_id, double price, int amount, List<String>  cities) {
        this.name = name;
        this.description = description;
        this.brand_id = brand_id;
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
                ", brand_id='" + brand_id + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", cities='" + cities + '\'' +
                '}';
    }
}
