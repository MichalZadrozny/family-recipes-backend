package pl.michalzadrozny.familyrecipes.service;

import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;

public interface UserService {
    void addNewUser(AppUser user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException;
}
