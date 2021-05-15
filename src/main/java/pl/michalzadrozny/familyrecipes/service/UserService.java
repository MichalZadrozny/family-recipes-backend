package pl.michalzadrozny.familyrecipes.service;

import javassist.NotFoundException;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.dto.AccountRecoveryDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;

public interface UserService {
    void addNewUser(AppUser user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;
    void changePassword(AccountRecoveryDTO recoveryDTO) throws NotFoundException;
}
