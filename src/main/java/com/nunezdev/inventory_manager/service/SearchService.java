package com.nunezdev.inventory_manager.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
    List<Object> searchAll(String searchTerm);
}