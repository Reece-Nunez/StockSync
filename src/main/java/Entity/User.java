package Entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Setter
@ToString
public class User extends BaseEntity {

    @NotNull
    @Size(min = 5, max = 12)
    private String username;

    @NotNull
    @Size(min = 5, max = 8)
    private String password;

    @NotNull
    private String role;

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }
}
