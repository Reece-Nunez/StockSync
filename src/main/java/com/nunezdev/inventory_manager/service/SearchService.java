package com.nunezdev.inventory_manager.service;

import com.nunezdev.inventory_manager.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    List<Object> searchAll(String searchTerm);
}