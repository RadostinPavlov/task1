package com.example.task1.model;

import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class FilterData {
    private double minPrice;
    private double maxPrice;
    private Set<String> availableSizes;
    private List<String> commonWords;
}
