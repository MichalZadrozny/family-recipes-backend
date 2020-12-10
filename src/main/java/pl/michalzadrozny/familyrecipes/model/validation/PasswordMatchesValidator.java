package pl.michalzadrozny.familyrecipes.model.validation;


import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final SignUpDTO user = (SignUpDTO) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}