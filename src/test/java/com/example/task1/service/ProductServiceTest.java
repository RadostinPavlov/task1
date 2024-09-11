package com.example.task1.service;

import com.example.task1.model.FilterData;
import com.example.task1.model.FilterResponse;
import com.example.task1.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    @Mock
    private RepositoryService repositoryService;

    @InjectMocks
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Product product1 = new Product("Stylish T-Shirt", 24.99, new String[]{"Small","Medium","Large"}, "Comfortable cotton t-shirt in red with a stylish design." );
        Product product2 = new Product("Classic Jeans", 34.99, new String[]{"Medium","Large"}, "Timeless classic jeans in blue for a casual and versatile look." );
        Product product3 = new Product("Cozy Hoodie", 16.99, new String[]{"Small", "Medium"}, "Warm and cozy hoodie in green for a relaxed and comfortable style." );

        products = Arrays.asList(product1, product2, product3);

        when(repositoryService.getProducts()).thenReturn(products);
    }

    @Test
    void testFilterProducts_NoFilters() {
        FilterResponse response = productService.filterProducts(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotNull(response);
        assertEquals(3, response.getProducts().size());
    }

    @Test
    void testFilterProducts_MinPrice() {
        FilterResponse response = productService.filterProducts(Optional.of(20.0), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertEquals("Classic Jeans", response.getProducts().get(1).getTitle());
    }

    @Test
    void testFilterProducts_MaxPrice() {
        FilterResponse response = productService.filterProducts(Optional.empty(), Optional.of(20.0), Optional.empty(), Optional.empty());

        assertNotNull(response);
        assertEquals(1, response.getProducts().size());
        assertEquals("Cozy Hoodie", response.getProducts().get(0).getTitle());
    }

    @Test
    void testFilterProducts_MinMaxPrice() {
        FilterResponse response = productService.filterProducts(Optional.of(30.0), Optional.of(50.0), Optional.empty(), Optional.empty());

        assertNotNull(response);
        assertEquals(1, response.getProducts().size());
        assertEquals("Classic Jeans", response.getProducts().get(0).getTitle());
    }

    @Test
    void testFilterProducts_Size() {
        FilterResponse response = productService.filterProducts(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("Small"));

        assertNotNull(response);
        assertEquals(2, response.getProducts().size());
        assertTrue(response.getProducts().stream().anyMatch(product -> product.getTitle().equals("Stylish T-Shirt")));
        assertTrue(response.getProducts().stream().anyMatch(product -> product.getTitle().equals("Cozy Hoodie")));
    }

    @Test
    void testApplyHighlighting() {
        FilterResponse response = productService.filterProducts(Optional.empty(), Optional.empty(), Optional.of("red,blue,green"), Optional.empty());

        assertNotNull(response);
        assertTrue(response.getProducts().get(0).getDescription().contains("<em>red</em>"));
        assertTrue(response.getProducts().get(1).getDescription().contains("<em>blue</em>"));
        assertTrue(response.getProducts().get(2).getDescription().contains("<em>green</em>"));
    }

    @Test
    void testCalculateMetadata() {
        FilterResponse response = productService.filterProducts(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

        assertNotNull(response);
        FilterData metadata = response.getMetadata();
        assertEquals(16.99, metadata.getMinPrice());
        assertEquals(34.99, metadata.getMaxPrice());
        assertEquals(3, metadata.getAvailableSizes().size());
        assertTrue(metadata.getCommonWords().size() > 0);
    }

    @Test
    void testFindMostCommonWords() {
        List<String> commonWords = productService.findMostCommonWords(products);

        assertNotNull(commonWords);
        assertTrue(commonWords.contains("cotton"));
        assertTrue(commonWords.size() <= 10);
    }
}
