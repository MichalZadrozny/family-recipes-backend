package pl.michalzadrozny.familyrecipes.service;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.VerificationToken;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;
import pl.michalzadrozny.familyrecipes.repository.VerificationTokenRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private VerificationTokenRepo verificationTokenRepo;

    @InjectMocks
    private RegistrationService registrationService;

    private static AppUser getSampleUser() {
        AppUser user = new AppUser();
        user.setUsername("testUser");
        user.setPassword("312345aD@");
        user.setEmail("testemail@email.com");
        return user;
    }

    private static VerificationToken getSampleVerificationToken() {

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setAppUser(getSampleUser());
        verificationToken.setValue("123");

        return verificationToken;
    }

    @Test
    void should_throwNotFoundException_when_verificationTokenDoesNotExist() {

//        given
        String token = "123";
        given(verificationTokenRepo.findByValue(token)).willReturn(Optional.empty());

//        when
//        then
        assertThatExceptionOfType(NotFoundException.class).isThrownBy(() -> registrationService.verifyToken(token));
    }

    @Test
    void should_notSaveUser_when_verificationTokenDoesNotExist() {

//        given
        VerificationToken verificationToken = getSampleVerificationToken();
        given(verificationTokenRepo.findByValue(verificationToken.getValue())).willReturn(Optional.empty());

//        when
        try {
            registrationService.verifyToken(verificationToken.getValue());
        } catch (NotFoundException e) {

        }

//        then
        verify(userRepo, never()).save(any(AppUser.class));
    }

    @Test
    void should_notDeleteToken_when_verificationTokenDoesNotExist() {

//        given
        VerificationToken verificationToken = getSampleVerificationToken();
        given(verificationTokenRepo.findByValue(verificationToken.getValue())).willReturn(Optional.empty());

//        when
        try {
            registrationService.verifyToken(verificationToken.getValue());
        } catch (NotFoundException e) {

        }

//        then
        verify(verificationTokenRepo, never()).delete(any(VerificationToken.class));
    }


    @Test
    void should_saveUserWithAccountEnabled_when_verificationTokenExist() throws NotFoundException {

//        given
        VerificationToken verificationToken = getSampleVerificationToken();
        given(verificationTokenRepo.findByValue(verificationToken.getValue())).willReturn(Optional.of(verificationToken));

//        when
        registrationService.verifyToken(verificationToken.getValue());

//        then
        assertThat(verificationToken.getAppUser().isEnabled()).isTrue();
        verify(userRepo, times(1)).save(verificationToken.getAppUser());
    }

    @Test
    void should_deleteToken_when_verificationTokenExist() throws NotFoundException {

//        given
        VerificationToken verificationToken = getSampleVerificationToken();
        given(verificationTokenRepo.findByValue(verificationToken.getValue())).willReturn(Optional.of(verificationToken));

//        when
        registrationService.verifyToken(verificationToken.getValue());

//        then
        verify(verificationTokenRepo, times(1)).delete(verificationToken);
    }

//    Spróbować z Argument Captorem

    @Test
    void should_saveToken_when_sendingActivationLink() {

//        given
//        when
        try {
            registrationService.sendActivationLink(getSampleUser());
        } catch (IllegalStateException e) {

        }

//        then
        verify(verificationTokenRepo, times(1)).save(any(VerificationToken.class));
    }
}