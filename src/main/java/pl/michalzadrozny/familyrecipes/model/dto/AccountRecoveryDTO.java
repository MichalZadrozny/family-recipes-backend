package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.Data;
import pl.michalzadrozny.familyrecipes.model.validation.PasswordMatches;

import javax.validation.constraints.Pattern;

@Data
@PasswordMatches(field = "password", fieldMatch = "confirmPassword")
public class AccountRecoveryDTO {
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Hasło musi zawierać minimum: 8 znaków, wielką literę, małą literę, cyfrę oraz znak specjalny")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Hasło musi zawierać minimum: 8 znaków, wielką literę, małą literę, cyfrę oraz znak specjalny")
    private String confirmPassword;

    private String token;
}