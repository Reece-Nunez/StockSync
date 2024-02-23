package com.nunezdev.inventory_manager.dto;

import com.nunezdev.inventory_manager.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String password;
    private String roleName;


}
