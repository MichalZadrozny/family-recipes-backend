package pl.michalzadrozny.familyrecipes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, RegistrationService registrationService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
    }

    public void addNewUser(AppUser user) throws UsernameAlreadyExistsException, EmailAlreadyExistsException {

        userRepo.findByUsername(user.getUsername()).ifPresent(s -> {
            throw new UsernameAlreadyExistsException();
        });
        userRepo.findByEmail(user.getEmail()).ifPresent(s -> {
            throw new EmailAlreadyExistsException("User with specified email already exists");
        });
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        registrationService.sendActivationLink(user);
    }
}