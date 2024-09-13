package com.example.task1.service;

import com.example.task1.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RepositoryServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RepositoryService repositoryService;


    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void getProducts_successful() throws Exception {
        String jsonResponse = "[{\"title\": \"Stylish T-Shirt\", \"price\": 24.99, \"size\": [\"Small\",\"Medium\",\"Large\"]},"
                + "{\"title\": \"Classic Jeans\", \"price\": 34.99, \"size\": [\"Medium\",\"Large\"]}]";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Product> products = repositoryService.getProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        assertEquals("Stylish T-Shirt", products.get(0).getTitle());
        assertEquals("Classic Jeans", products.get(1).getTitle());
    }

    @Test
    void testGetProducts_InvalidJsonResponse() {
        String invalidJsonResponse = "Invalid JSON";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(invalidJsonResponse, HttpStatus.OK);
        when(restTemplate.getForEntity(anyString(), eq(String.class))).thenReturn(responseEntity);

        List<Product> products = repositoryService.getProducts();

        verify(restTemplate, times(1)).getForEntity(anyString(), eq(String.class));
        assertNull(products);
    }

    @Test
    void testGetProducts_ApiCallFails() {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenThrow(new RuntimeException("API call failed"));

        assertThrows(RuntimeException.class, () -> repositoryService.getProducts());
    }
}

