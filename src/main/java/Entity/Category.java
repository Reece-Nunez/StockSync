package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categories")
@Getter @Setter @ToString @NoArgsConstructor
public class Category extends BaseEntity {

    private String name;
    private String description;
}
