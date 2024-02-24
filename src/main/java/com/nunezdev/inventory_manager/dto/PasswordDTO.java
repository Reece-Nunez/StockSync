package com.nunezdev.inventory_manager.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class PasswordDTO {
    private String newPassword;
    private String oldPassword;

}
