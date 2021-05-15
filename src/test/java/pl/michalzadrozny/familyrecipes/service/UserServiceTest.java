package pl.michalzadrozny.familyrecipes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.repository.RecoveryTokenRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private RecoveryTokenRepo recoveryTokenRepo;

    @Mock
    private RegistrationService registrationService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepo, passwordEncoder, registrationService, recoveryTokenRepo);
    }

    private static AppUser getSampleUser() {
        AppUser user = new AppUser();
        user.setUsername("testUser");
        user.setPassword("312345aD@");
        user.setEmail("testemail@email.com");
        return user;
    }

    @Test
    void should_throwUsernameAlreadyExistsException_when_usernameIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.of(user));

//        when
//        then
        assertThatExceptionOfType(UsernameAlreadyExistsException.class).isThrownBy(() -> userService.addNewUser(user));
    }

    @Test
    void should_throwEmailAlreadyExistsException_when_emailIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));

//        when
//        then
        assertThatExceptionOfType(EmailAlreadyExistsException.class).isThrownBy(() -> userService.addNewUser(user));
    }

    @Test
    void should_sendEmailOnce_when_userDetailsAreCorrect() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.empty());
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());

//        when
        userService.addNewUser(user);

//        then
        verify(registrationService, times(1)).sendActivationLink(user);
    }

    @Test
    void should_saveUserOnce_when_userDetailsAreCorrect() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.empty());
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());

//        when
        userService.addNewUser(user);

//        then
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void should_encryptPassword_when_userDetailsAreCorrect() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.empty());
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.empty());
        String notEncryptedPassword = user.getPassword();

//        when
        userService.addNewUser(user);

//        then
        assertThat(notEncryptedPassword).isNotEqualTo(user.getPassword());
        assertThat(passwordEncoder.matches(notEncryptedPassword, user.getPassword())).isTrue();
    }

    @Test
    void should_notSaveUser_when_usernameIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.of(user));

//        when
        try {
            userService.addNewUser(user);
        } catch (UsernameAlreadyExistsException e) {
        }

//        then
        verify(userRepo, never()).save(user);
    }

    @Test
    void should_notSaveUser_when_emailIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));

//        when
        try {
            userService.addNewUser(user);
        } catch (EmailAlreadyExistsException e) {
        }

//        then
        verify(userRepo, never()).save(user);
    }

    @Test
    void should_notSendEmail_when_usernameIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByUsername(user.getUsername())).willReturn(Optional.of(user));

//        when
        try {
            userService.addNewUser(user);
        } catch (UsernameAlreadyExistsException e) {
        }

//        then
        verify(registrationService, never()).sendActivationLink(user);
    }

    @Test
    void should_notSendEmail_when_emailIsTaken() {

//        given
        AppUser user = getSampleUser();
        given(userRepo.findByEmail(user.getEmail())).willReturn(Optional.of(user));

//        when
        try {
            userService.addNewUser(user);
        } catch (EmailAlreadyExistsException e) {
        }

//        then
        verify(registrationService, never()).sendActivationLink(user);
    }
}