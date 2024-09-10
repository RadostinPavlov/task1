package com.example.task1.controller;

import com.example.task1.model.FilterResponse;
import com.example.task1.model.Product;
import com.example.task1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/filter")
    public ResponseEntity<FilterResponse> filterProducts(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String highlight,
            @RequestParam(required = false) String size) {

        FilterResponse response = productService.filterProducts(
                Optional.ofNullable(minPrice),
                Optional.ofNullable(maxPrice),
                Optional.ofNullable(highlight),
                Optional.ofNullable(size)
        );

        return ResponseEntity.ok(response);
    }
}
