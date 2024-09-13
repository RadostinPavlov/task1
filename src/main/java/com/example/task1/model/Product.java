package com.example.task1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.Arrays;
import java.util.List;

@Data
public class Product {

    private String title;
    private double price;
    private String description;
    private String[] size;

    @JsonCreator
    public Product(@JsonProperty("Title") String title,
                   @JsonProperty("Price") double price,
                   @JsonProperty("Sizes") String[] sizes,
                   @JsonProperty("Description") String description) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.size = sizes;
    }

    public List<String> getSizes() {
        return Arrays.stream(this.size).toList();
    }
}
