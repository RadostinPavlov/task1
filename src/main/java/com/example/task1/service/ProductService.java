package com.example.task1.service;

import com.example.task1.model.FilterData;
import com.example.task1.model.FilterResponse;
import com.example.task1.model.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private List<Product> products;

    private final RepositoryService repositoryService;

    public ProductService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public FilterResponse filterProducts(Optional<Double> minPrice,
                                         Optional<Double> maxPrice,
                                         Optional<String> highlight,
                                         Optional<String> size) {
        List<Product> products = repositoryService.getProducts();
        List<Product> filteredProducts = products.stream()
                .filter(product -> minPrice.map(mp -> product.getPrice() >= mp).orElse(true))
                .filter(product -> maxPrice.map(mp -> product.getPrice() <= mp).orElse(true))
                .filter(product -> size.map(s -> Arrays.stream(product.getSize()).anyMatch(sz -> sz.equalsIgnoreCase(s))).orElse(true))
                .collect(Collectors.toList());

        highlight.ifPresent(words -> applyHighlighting(filteredProducts, words));
        FilterData metadata = calculateMetadata(products);

        return new FilterResponse(filteredProducts, metadata);
    }


    private void applyHighlighting(List<Product> products, String words) {
        String[] highlightWords = words.split(",");
        for (Product product : products) {
            String highlightedDescription = product.getDescription();
            for (String word : highlightWords) {
                highlightedDescription = highlightedDescription.replaceAll("(?i)" + word, "<em>" + word + "</em>");
            }
            product.setDescription(highlightedDescription);
        }
    }

    private FilterData calculateMetadata(List<Product> products) {
        double minPrice = products.stream().mapToDouble(Product::getPrice).min().orElse(0);
        double maxPrice = products.stream().mapToDouble(Product::getPrice).max().orElse(0);
        Set<String> sizes = new HashSet<>();
        for (Product product : products) {
            sizes.addAll(product.getSizes());
        }
        List<String> commonWords = findMostCommonWords(products);

        FilterData metadata = new FilterData();
        metadata.setMinPrice(minPrice);
        metadata.setMaxPrice(maxPrice);
        metadata.setAvailableSizes(sizes);
        metadata.setCommonWords(commonWords);
        return metadata;
    }


    private List<String> findMostCommonWords(List<Product> products) {
        Map<String, Long> wordCount = new HashMap<>();
        for (Product product : products) {
            String[] words = product.getDescription().toLowerCase().split("\\W+");
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0L) + 1);
            }
        }

        List<String> result =  wordCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(15)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return result.subList(5, result.size());
    }
}
