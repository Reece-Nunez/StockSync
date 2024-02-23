package Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private long id;
    private String username;
    private String role;


    public UserDTO(long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
