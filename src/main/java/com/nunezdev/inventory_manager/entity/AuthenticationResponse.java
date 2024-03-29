package com.nunezdev.inventory_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String token;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Long id;

}
