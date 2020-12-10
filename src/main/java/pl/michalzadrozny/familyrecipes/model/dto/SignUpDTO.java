package pl.michalzadrozny.familyrecipes.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import pl.michalzadrozny.familyrecipes.model.validation.PasswordMatches;

import javax.validation.constraints.*;

@PasswordMatches
@Data
public class SignUpDTO {

    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    @Size(max = 15)
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Hasło musi zawierać minimum: 8 znaków, wielką literę, małą literę, cyfrę oraz znak specjalny")
    private String password;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Hasło musi zawierać minimum: 8 znaków, wielką literę, małą literę, cyfrę oraz znak specjalny")
    private String confirmPassword;

    @Email(message = "Nieprawidłowy adres email")
    @NotBlank(message = "Email nie może być pusty")
    private String email;

    @ApiModelProperty(allowableValues = "true")
    @AssertTrue(message = "Akceptacja Regulaminu jest wymagana")
    private boolean termsOfUse;
}