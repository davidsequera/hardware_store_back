package com.hardware.tools.domain.inputs;

import com.hardware.tools.domain.entities.Tool;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class ToolInput {
    public String id;

    public String name;
    public String description;
    public String brandId;
    public double price;
    public int amount;
    public List<String> cities;

    public Tool toTool(){
        if (id == null) {
            return new Tool(name, description,brandId, price, amount, cities);
        }
        return new Tool(id, name, description,brandId, price, amount, cities);
    }

    @Override
    public String toString() {
        return "createToolInput{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", brandId='" + brandId + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", cities=" + cities +
                '}';
    }
}
