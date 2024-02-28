package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.repository.ProductRepository;
import com.nunezdev.inventory_manager.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public SearchController(SearchService searchService, ModelMapper modelMapper, ProductRepository productRepository) {
        this.searchService = searchService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @GetMapping("/results")
    public List<ProductDTO> searchProducts(@RequestParam String searchTerm) {
        return productRepository.searchByTerm(searchTerm).stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}