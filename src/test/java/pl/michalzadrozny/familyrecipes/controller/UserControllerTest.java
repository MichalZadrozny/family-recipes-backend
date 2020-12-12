package pl.michalzadrozny.familyrecipes.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.michalzadrozny.familyrecipes.exception.EmailAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.exception.UsernameAlreadyExistsException;
import pl.michalzadrozny.familyrecipes.model.dto.LoginDTO;
import pl.michalzadrozny.familyrecipes.model.dto.SignUpDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;
import pl.michalzadrozny.familyrecipes.security.UserDetailsServiceImpl;
import pl.michalzadrozny.familyrecipes.security.WebSecurityConfig;
import pl.michalzadrozny.familyrecipes.service.RegistrationService;
import pl.michalzadrozny.familyrecipes.service.UserService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doThrow;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = UserController.class)
@Import({WebSecurityConfig.class, UserDetailsServiceImpl.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private JacksonTester<SignUpDTO> signUpJacksonTester;

    @Autowired
    private JacksonTester<LoginDTO> loginJacksonTester;

    private static AppUser getSampleUser() {
        AppUser user = new AppUser();
        user.setUsername("testUser");
        user.setPassword("312345aD@");
        user.setEmail("testemail@email.com");
        return user;
    }

    private static SignUpDTO getValidSignUpDTO() {
        SignUpDTO user = new SignUpDTO();
        user.setUsername("testUser");
        user.setPassword("312345aD@");
        user.setConfirmPassword("312345aD@");
        user.setEmail("testemail@email.com");
        user.setTermsOfUse(true);
        return user;
    }

//    SING-UP

    @Test
    void should_returnCreatedStatus_when_userDetailsAreValid() throws Exception {

//        given
//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(getValidSignUpDTO()).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isBlank();
    }

    @Test
    void should_returnBadRequestStatus_when_exceptionIsThrown() throws Exception {

//        given
        doThrow(RuntimeException.class).when(userService).addNewUser(getSampleUser());

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(getValidSignUpDTO()).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    USERNAME OF EMAIL ARE TAKEN

    @Test
    void should_returnConflictStatus_when_usernameIsTaken() throws Exception {

//        given
        doThrow(UsernameAlreadyExistsException.class).when(userService).addNewUser(getSampleUser());

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(getValidSignUpDTO()).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void should_returnConflictStatus_when_emailIsTaken() throws Exception {

//        given
        doThrow(EmailAlreadyExistsException.class).when(userService).addNewUser(getSampleUser());

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(getValidSignUpDTO()).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

//    USERNAME

    @Test
    void should_returnBadRequestStatus_when_usernameIsNull() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setUsername(null);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_usernameIsEmpty() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setUsername("  ");

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    EMAIL

    @Test
    void should_returnBadRequestStatus_when_emailIsNull() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setEmail(null);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_emailIsEmpty() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setEmail("  ");

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_emailDoesNotContainAtSign() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setEmail("testemail.com");

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    TERMS OF USE

    @Test
    void should_returnBadRequestStatus_when_termsOfUseIsFalse() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setTermsOfUse(false);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    PASSWORDS

    @Test
    void should_returnBadRequestStatus_when_passwordsDoNotMatch() throws Exception {

//        given
        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setConfirmPassword(signUpDTO.getPassword() + "error");

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_passwordDoesNotContainAtLeast8Characters() throws Exception {

//        given
        String invalidPassword = "312taD@";

        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setPassword(invalidPassword);
        signUpDTO.setConfirmPassword(invalidPassword);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_passwordDoesNotContainNumbers() throws Exception {

//        given
        String invalidPassword = "testpasswordD@";

        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setPassword(invalidPassword);
        signUpDTO.setConfirmPassword(invalidPassword);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_passwordDoesNotContainUppercaseLetters() throws Exception {

//        given
        String invalidPassword = "312345test@";

        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setPassword(invalidPassword);
        signUpDTO.setConfirmPassword(invalidPassword);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_passwordDoesNotContainLowercaseLetters() throws Exception {

//        given
        String invalidPassword = "312345TEST@";

        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setPassword(invalidPassword);
        signUpDTO.setConfirmPassword(invalidPassword);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void should_returnBadRequestStatus_when_passwordDoesNotContainSpecialSigns() throws Exception {

//        given
        String invalidPassword = "312345TEST";

        SignUpDTO signUpDTO = getValidSignUpDTO();
        signUpDTO.setPassword(invalidPassword);
        signUpDTO.setConfirmPassword(invalidPassword);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.post("/api/user/sign-up")
                        .characterEncoding("utf-8")
                        .content(signUpJacksonTester.write(signUpDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

//    VERIFY TOKEN

    @Test
    void should_returnOkStatus_when_exceptionIsNotThrown() throws Exception {

//        given
//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/user/verify-token")
                        .param("token", "123"))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_returnNotFoundStatus_when_NotFoundExceptionIsThrown() throws Exception {

//        given
        String token = "123";
        doThrow(NotFoundException.class).when(registrationService).verifyToken(token);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/user/verify-token")
                        .param("token", token))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnNotFoundStatus_when_ExceptionIsThrown() throws Exception {

//        given
        String token = "123";
        doThrow(RuntimeException.class).when(registrationService).verifyToken(token);

//        when
        MockHttpServletResponse response = mockMvc.
                perform(MockMvcRequestBuilders.get("/api/user/verify-token")
                        .param("token", token))
                .andReturn().getResponse();

//        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}