package com.nunezdev.inventory_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter @Setter @ToString @NoArgsConstructor
public class Transaction extends BaseEntity {

    @ManyToOne
    private Product product;
    @ManyToOne
    private AppUser appUser;
    private Integer quantity;
    private LocalDateTime transactionTime;
    private String transactionType; // Could be either "buy" or "sell"
}
