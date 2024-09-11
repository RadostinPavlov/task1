package com.example.task1.service;

import com.example.task1.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RepositoryService {
    private final RestTemplate restTemplate;

    @Autowired
    public RepositoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getProducts() {
        String url = "https://run.mocky.io/v3/7af94347-277c-4d9e-8c4a-02f2c8573871";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String json = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Product> products = objectMapper.readValue(json,  new TypeReference<List<Product>>() {});
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
