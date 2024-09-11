package com.example.task1.controller;

import com.example.task1.model.FilterResponse;
import com.example.task1.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void testFilterProducts_NoParams() throws Exception {
        FilterResponse mockResponse = new FilterResponse();
        when(productService.filterProducts(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/filter")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFilterProducts_WithMinMaxPrice() throws Exception {
        FilterResponse mockResponse = new FilterResponse();
        when(productService.filterProducts(
                Optional.of(10.0),
                Optional.of(100.0),
                Optional.empty(),
                Optional.empty()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/filter")
                        .param("minPrice", "10")
                        .param("maxPrice", "100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFilterProducts_WithSize() throws Exception {
        FilterResponse mockResponse = new FilterResponse();
        when(productService.filterProducts(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of("Medium")))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/filter")
                        .param("size", "Medium")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFilterProducts_WithHighlight() throws Exception {

        FilterResponse mockResponse = new FilterResponse();
        when(productService.filterProducts(
                Optional.empty(),
                Optional.empty(),
                Optional.of("red,blue"),
                Optional.empty()))
                .thenReturn(mockResponse);

        mockMvc.perform(get("/filter")
                        .param("highlight", "red,blue")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

