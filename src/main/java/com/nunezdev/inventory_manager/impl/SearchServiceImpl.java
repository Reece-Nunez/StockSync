package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.service.ProductService;
import com.nunezdev.inventory_manager.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private final ProductService productService;

    @Autowired
    public SearchServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Object> searchAll(String searchTerm) {
        List<Object> results = new ArrayList<>();
        results.addAll(productService.searchProducts(searchTerm));
        return results;
    }
}