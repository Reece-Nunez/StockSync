package Entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
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
}
