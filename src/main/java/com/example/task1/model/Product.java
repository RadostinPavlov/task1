package com.example.task1.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Product {

//    @JsonProperty("Title")
    private String title;
//    @JsonProperty("Price")
    private double price;
//    @JsonProperty("Description")
    private String description;
//    @JsonProperty("Sizes")
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
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public String[] getSize() {
//        return size;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setPrice(double price) {
//        this.price = price;
//    }
//
//    public void setSize(String[] size) {
//        this.size = size;
//    }
}
