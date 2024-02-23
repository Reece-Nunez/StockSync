package com.nunezdev.inventory_manager.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String role;
}
