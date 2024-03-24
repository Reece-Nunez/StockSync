package com.nunezdev.inventory_manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class UserDTO {

    private long id;

    @NotNull
    @Size(min = 5, max = 12)
    private String username;

    @NotNull
    @Size(min = 5, max = 15)
    private String password;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phoneNumber;

    private String role;
}
