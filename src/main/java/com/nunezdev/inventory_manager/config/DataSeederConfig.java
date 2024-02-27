package com.nunezdev.inventory_manager.config;

import com.nunezdev.inventory_manager.entity.Category;
import com.nunezdev.inventory_manager.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeederConfig {

    @Bean
    CommandLineRunner commandLineRunner(CategoryRepository categoryRepository) {
        return args -> {
            createCategoryIfNotFound(categoryRepository, "Category 1", "Description 1");
            createCategoryIfNotFound(categoryRepository, "Category 2", "Description 2");
            createCategoryIfNotFound(categoryRepository, "Category 3", "Description 3");
        };
    }

    private void createCategoryIfNotFound(CategoryRepository categoryRepository, String name, String description) {
        categoryRepository.findByName(name).orElseGet(() -> {
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            return categoryRepository.save(category);
        });
    }
}
