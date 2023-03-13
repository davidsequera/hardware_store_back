package com.hardware.tools.domain.entities;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
@NoArgsConstructor
@Document(collection = "tools")
public class Tool {
    @Id
    private String id;
    private String name;
    private String description;
    @Field("brand")
    private ObjectId brandId;
    private double price;
    private int amount;
    private List<String> cities;

    // Constructors
    public Tool(String id, String name, String description, String brandId, double price, int amount, List<String>  cities) {
        this.id = id;
        this.name = name;
        this.description = description;
//        TODO: Fix update tool
        if (brandId != null)
            this.brandId = new ObjectId(brandId);
        this.price = price;
        this.amount = amount;
        this.cities = cities;
    }
    public Tool(String name, String description, String brandId, double price, int amount, List<String>  cities) {
        this.name = name;
        this.description = description;
        this.brandId = new ObjectId(brandId);
        this.price = price;
        this.amount = amount;
        this.cities = cities;
    }


    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = Tool.this.brandId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
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
