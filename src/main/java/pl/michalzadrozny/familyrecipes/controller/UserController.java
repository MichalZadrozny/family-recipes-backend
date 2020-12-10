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
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.AppUser;
import pl.michalzadrozny.familyrecipes.model.Mapper;
import pl.michalzadrozny.familyrecipes.model.dto.LoginDTO;
import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;
import pl.michalzadrozny.familyrecipes.service.RegistrationService;
import pl.michalzadrozny.familyrecipes.service.UserService;

import javax.validation.Valid;

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
            userService.addNewUser(Mapper.signUpToAppUserMapper().map(user, AppUser.class));
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (UsernameAlreadyExistsException | EmailAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/verify-token")
    public ResponseEntity<Void> verifyToken(@RequestParam String token) {
        try {
            registrationService.verifyToken(token);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "login", notes = "Logging in requires prior verification of the e-mail address")
    @PostMapping("/login")
    @ApiResponses({@ApiResponse(code = 200, message = "OK")})
    public void fakeLogin(@RequestBody LoginDTO user) {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
    }
}