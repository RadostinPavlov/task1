package com.example.task1.controller;

import com.example.task1.model.FilterResponse;
import com.example.task1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProductController {

    private static final String MIN_ERROR = "Min price must not be negative.";
    private static final String MAX_ERROR = "Max price must not be negative.";
    private static final String MIN_MAX_ERROR = "Min price cannot be greater than Max price.";
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/filter")
    public ResponseEntity<? extends Object> filterProducts(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String highlight,
            @RequestParam(required = false) String size) {

        if (minPrice != null && minPrice < 0) {
            return ResponseEntity.badRequest().body(MIN_ERROR);
        }
        if (maxPrice != null && maxPrice < 0) {
            return ResponseEntity.badRequest().body(MAX_ERROR);
        }
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            return ResponseEntity.badRequest().body(MIN_MAX_ERROR);
        }

        FilterResponse response = productService.filterProducts(
                Optional.ofNullable(minPrice),
                Optional.ofNullable(maxPrice),
                Optional.ofNullable(highlight),
                Optional.ofNullable(size)
        );

        return ResponseEntity.ok(response);
    }
}
