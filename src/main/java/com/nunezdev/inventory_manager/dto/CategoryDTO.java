package com.nunezdev.inventory_manager.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;
}
