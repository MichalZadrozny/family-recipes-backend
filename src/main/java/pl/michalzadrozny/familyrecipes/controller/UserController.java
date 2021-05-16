package pl.michalzadrozny.familyrecipes.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.dto.AccountRecoveryDTO;
import pl.michalzadrozny.familyrecipes.model.dto.LoginDTO;
import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.mapper.UserMapper;
import pl.michalzadrozny.familyrecipes.service.RegistrationService;
import pl.michalzadrozny.familyrecipes.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static pl.michalzadrozny.familyrecipes.configuration.UrlConstants.WEBSITE_LOGIN_URL;
import static pl.michalzadrozny.familyrecipes.configuration.UrlConstants.WEBSITE_RECOVERY_URL;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final RegistrationService registrationService;


    @Autowired
    public UserController(UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @PostMapping("/sign-up")
    @ApiResponses({@ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 409, message = "Conflict")})
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDTO user) {
        try {
            userService.addNewUser(UserMapper.signUpDtoToAppUserMapper().map(user, AppUser.class));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Podana nazwa użytkownika jest już zajęta");
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Podany adres email jest już zajęty");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyRegistrationToken(@RequestParam String token) {
        try {
            registrationService.verifyToken(token);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/recover-password")
    public ResponseEntity<?> recoverPassword(@RequestParam String email) {
        try {
            registrationService.sendRecoveryLink(email);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Użytkownik o podanym adresie email nie istnieje");
        }
    }

    @GetMapping("/change-password")
    public void verifyRecoveryToken(@RequestParam String token, HttpServletResponse response) throws IOException {
        try {
            registrationService.verifyRecoveryToken(token);
            response.sendRedirect(WEBSITE_RECOVERY_URL+"/"+token);
        } catch (NotFoundException e) {
            response.sendRedirect(WEBSITE_LOGIN_URL);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody AccountRecoveryDTO recoveryDTO) {
        try {
            userService.changePassword(recoveryDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @ApiOperation(value = "login", notes = "Logging in requires prior verification of the e-mail address")
    @PostMapping("/login")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public void fakeLogin(@RequestBody LoginDTO user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}