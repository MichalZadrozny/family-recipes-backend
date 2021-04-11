package pl.michalzadrozny.familyrecipes.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpServerErrorException;
import pl.michalzadrozny.familyrecipes.PrepareTests;
import pl.michalzadrozny.familyrecipes.exception.InternalServerErrorException;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.entity.*;
import pl.michalzadrozny.familyrecipes.model.mapper.RecipeMapper;
import pl.michalzadrozny.familyrecipes.model.view.IngredientsView;
import pl.michalzadrozny.familyrecipes.model.view.RecipeView;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;
import pl.michalzadrozny.familyrecipes.security.UserDetailsServiceImpl;
import pl.michalzadrozny.familyrecipes.security.WebSecurityConfig;
import pl.michalzadrozny.familyrecipes.service.RecipeServiceImpl;
import pl.michalzadrozny.familyrecipes.service.StorageService;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@AutoConfigureJsonTesters
@WebMvcTest(RecipeController.class)
@Import({WebSecurityConfig.class, UserDetailsServiceImpl.class})
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private RecipeRepo recipeRepo;

    @MockBean
    private RecipeServiceImpl recipeService;

    @Autowired
    private JacksonTester<Recipe> recipeJacksonTester;

    @Autowired
    private JacksonTester<RecipeDTO> recipeDTOJacksonTester;
    @Autowired
    private JacksonTester<AddRecipeDTO> addRecipeDTOJacksonTester;

    @Autowired
    private JacksonTester<RecipeView> recipeViewJacksonTester;

    @Autowired
    private JacksonTester<List<RecipePreviewDTO>> recipePreviewListJacksonTester;



//    GET RECIPE

    @Test
    void should_returnForbiddenStatus_when_gettingRecipeWithoutAToken() throws Exception {
        //        given
        RecipeView recipe = PrepareTests.getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.of(recipe));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId()))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void should_returnOkStatus_when_recipeHasBeenFound() throws Exception {
        //        given
        RecipeView recipe = PrepareTests.getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.of(recipe));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_returnRecipe_when_recipeHasBeenFound() throws Exception {
        //        given
        RecipeView recipe = PrepareTests.getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.of(recipe));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipeViewJacksonTester.write(recipe).getJson());
    }

    @Test
    void should_returnNotFoundStatus_when_recipeHasNotBeenFound() throws Exception {
        //        given
        RecipeView recipe = PrepareTests.getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.empty());

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

//    ADD RECIPE

    @Test
    void should_returnForbiddenStatus_when_addingRecipeWithoutAToken() throws Exception {
        //        given
        RecipeDTO recipe = PrepareTests.getValidRecipeDTO();

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/recipes")
                        .characterEncoding("utf-8")
                        .content(recipeDTOJacksonTester.write(recipe).getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void should_returnCreatedStatus_when_addingRecipe() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file("image", PrepareTests.getMultipartFile().getBytes())
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void should_returnCreatedStatus_when_addingRecipeWithoutImage() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void should_returnNotFoundStatus_when_addingRecipeByUnknownUser() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));
        doThrow(UserDoesNotExistException.class).when(recipeService).addRecipe(addRecipeDTO, null);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnConflictStatus_when_addingRecipeThatAlreadyExit() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));
        doThrow(RecipeAlreadyExistException.class).when(recipeService).addRecipe(addRecipeDTO, null);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void should_returnInternalServerErrorStatus_when_internalServerErrorExceptionIsThrown() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));
        doThrow(InternalServerErrorException.class).when(recipeService).addRecipe(addRecipeDTO, null);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void should_executeAddRecipe_when_addingRecipe() throws Exception {
        //        given
        AddRecipeDTO addRecipeDTO = PrepareTests.getValidAddRecipeDTO();
        MockMultipartFile multipartRecipe = new MockMultipartFile("recipe", "","application/json", PrepareTests.serialize(addRecipeDTO));
        RecipeDTO recipeDTO = PrepareTests.getValidRecipeDTO();
        given(recipeService.addRecipe(addRecipeDTO, null)).willReturn(recipeDTO);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(multipart("/api/recipes")
                        .file(multipartRecipe)
                        .with(SecurityMockMvcRequestPostProcessors.user(PrepareTests.getSampleUser())))
                .andReturn().getResponse();

        //        then
        AddRecipeDTO output = new Gson().fromJson(response.getContentAsString(), AddRecipeDTO.class);
        verify(recipeService, times(1)).addRecipe(output, null);
    }

//   GET RECIPE PREVIEWS

    @Test
    void should_returnOkStatus_when_returningRecipePreviews() throws Exception {
        //        given
        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes"))
                .andReturn()
                .getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_returnEmptyRecipePreviewsList_when_repositoryReturnsNothing() throws Exception {
        //        given
        given(recipeRepo.findAllRecipePreviews(PageRequest.of(0, 10))).willReturn(Collections.emptyList());

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes" + "?page=1&size=10"))
                .andReturn()
                .getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipePreviewListJacksonTester.write(Collections.emptyList()).getJson());
    }

    @Test
    void should_listOfRecipePreviews_when_repositoryReturnsFilledList() throws Exception {
        //        given
        given(recipeRepo.findAllRecipePreviews(PageRequest.of(1, 10))).willReturn(List.of(PrepareTests.getValidRecipePreviewDTO()));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes" + "?page=1&size=10"))
                .andReturn()
                .getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipePreviewListJacksonTester.write(List.of(PrepareTests.getValidRecipePreviewDTO())).getJson());
    }

//    EDIT RECIPE

}