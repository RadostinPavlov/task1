package com.example.task1.model;

import lombok.Data;
import java.util.List;

@Data
public class FilterResponse {

    private List<Product> products;
    private FilterData filter;

    public FilterResponse(List<Product> filteredProducts, FilterData metadata) {
        products = filteredProducts;
        filter = metadata;
    }

    public FilterResponse() {}

    public FilterData getMetadata() {
        return filter;
    }
}
