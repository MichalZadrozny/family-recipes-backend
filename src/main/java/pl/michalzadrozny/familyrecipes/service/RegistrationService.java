package pl.michalzadrozny.familyrecipes.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.VerificationToken;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;
import pl.michalzadrozny.familyrecipes.repository.VerificationTokenRepo;

import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationService {

    private final UserRepo userRepo;
    private final EmailService emailService;
    private final VerificationTokenRepo verificationTokenRepo;

    @Autowired
    public RegistrationService(UserRepo userRepo, EmailService emailService, VerificationTokenRepo verificationTokenRepo) {
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.verificationTokenRepo = verificationTokenRepo;
    }

    public String getContentWithLink(String token, String content) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/verify-token").queryParam("token", token).toUriString();

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

    public void sendActivationLink(AppUser user) {
        String token = saveVerificationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                "Twój link aktywacyjny",
                getContentWithLink(token, "Klliknij w link poniżej aby aktywować konto: \n"));
    }

    private String saveVerificationToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);
        return token;
    }
}