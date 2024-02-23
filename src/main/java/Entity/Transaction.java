package Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Transaction extends BaseEntity {

    @ManyToOne
    private Product product;
    @ManyToOne
    private AppUser appUser;
    private Integer quantity;
    private LocalDateTime transactionTime;
    private String transactionType; // Could be either "buy" or "sell"
}
