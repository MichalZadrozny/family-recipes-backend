package pl.michalzadrozny.familyrecipes.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.dto.AccountRecoveryDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.RecoveryToken;
import pl.michalzadrozny.familyrecipes.repository.RecoveryTokenRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RegistrationService registrationService;
    private final RecoveryTokenRepo recoveryTokenRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, RegistrationService registrationService, RecoveryTokenRepo recoveryTokenRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
        this.recoveryTokenRepo = recoveryTokenRepo;
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

    public void changePassword(AccountRecoveryDTO recoveryDTO) throws NotFoundException {
        RecoveryToken recoveryToken = recoveryTokenRepo.findByValue(recoveryDTO.getToken()).orElseThrow(() -> new NotFoundException("Couldn't found recovery token with value: " + recoveryDTO.getToken()));

        AppUser user = recoveryToken.getAppUser();

        user.setPassword(passwordEncoder.encode(recoveryDTO.getPassword()));
        userRepo.save(user);

        recoveryTokenRepo.delete(recoveryToken);
    }
}