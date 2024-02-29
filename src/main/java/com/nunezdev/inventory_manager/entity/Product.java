package com.nunezdev.inventory_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

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

    private ZonedDateTime dateCreated;
}
