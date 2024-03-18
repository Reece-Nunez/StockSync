package com.nunezdev.inventory_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter @Setter @ToString @NoArgsConstructor
public class Product extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;
    @Size(max=200)
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private Integer quantity;
    private LocalDate dateCreated;
}
