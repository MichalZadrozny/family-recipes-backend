package pl.michalzadrozny.familyrecipes.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.RecoveryToken;
import pl.michalzadrozny.familyrecipes.model.entity.VerificationToken;
import pl.michalzadrozny.familyrecipes.repository.RecoveryTokenRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;
import pl.michalzadrozny.familyrecipes.repository.VerificationTokenRepo;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final EmailService emailService;
    private final VerificationTokenRepo verificationTokenRepo;
    private final RecoveryTokenRepo recoveryTokenRepo;

    @Autowired
    public RegistrationService(UserRepo userRepo, EmailService emailService, VerificationTokenRepo verificationTokenRepo, RecoveryTokenRepo recoveryTokenRepo) {
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.verificationTokenRepo = verificationTokenRepo;
        this.recoveryTokenRepo = recoveryTokenRepo;
    }

    public String getContentWithLink(String path, String token, String content) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(path).queryParam("token", token).toUriString();
        return content + uri;
    }

    public void verifyToken(String token) throws NotFoundException {
        Optional<VerificationToken> verificationToken = verificationTokenRepo.findByValue(token);

        if (verificationToken.isEmpty()) {
            throw new NotFoundException("Nie udało się znaleźć tokena weryfikacyjnego o wartości: " + token);
        }

        AppUser user = verificationToken.get().getAppUser();
        user.setAccountEnabled(true);

        userRepo.save(user);
        verificationTokenRepo.delete(verificationToken.get());
    }

    public void verifyRecoveryToken(String token) throws NotFoundException {
        recoveryTokenRepo.findByValue(token).orElseThrow(() -> new NotFoundException("Couldn't found recovery token with value: " + token));
    }

    public void sendActivationLink(AppUser user) {
        String token = saveVerificationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                "Twój link aktywacyjny",
                getContentWithLink("/api/user/verify-token", token, "Klliknij w link poniżej aby aktywować konto: \n"));
    }

    public void sendRecoveryLink(String email) throws UserDoesNotExistException {

        AppUser user = userRepo.findByEmail(email).orElseThrow(() -> new UserDoesNotExistException("User with email " + email + " does not exist"));

        String token = saveRecoveryToken(user);

        emailService.sendEmail(
                user.getEmail(),
                "Twój link do zresetowania hasła",
                getContentWithLink("/api/user/change-password", token, "Klliknij w link poniżej aby przejść do resetowania hasła: \n"));
    }

    private String saveVerificationToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);
        return token;
    }

    private String saveRecoveryToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        RecoveryToken recoveryToken = new RecoveryToken(user, token);
        recoveryTokenRepo.save(recoveryToken);
        return token;
    }
}