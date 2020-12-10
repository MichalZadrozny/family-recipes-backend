package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
}