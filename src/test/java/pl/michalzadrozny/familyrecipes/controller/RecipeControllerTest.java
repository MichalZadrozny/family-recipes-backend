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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
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
import pl.michalzadrozny.familyrecipes.service.RecipeService;
import pl.michalzadrozny.familyrecipes.service.RecipeServiceImpl;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    private JacksonTester<RecipeView> recipeViewJacksonTester;

    @Autowired
    private JacksonTester<List<RecipePreviewDTO>> recipePreviewListJacksonTester;

    private static AppUser getSampleUser() {
        return new AppUser(1L, "testUser", "312345aD@", "testemail@email.com", true);
    }

    private static RecipeDTO getValidRecipeDTO() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");

        AppUser user = getSampleUser();

        RecipeDTO recipeDTO = new RecipeDTO(1L, "Test name", user.getUsername(), 15, rating, "Test description", null, nutrients, Diet.VEGETARIAN, steps);

        Recipe recipe = RecipeMapper.recipeDtoToRecipeMapper(user).map(recipeDTO, Recipe.class);
        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipeDTO.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipeDTO;
    }

    private static Recipe getValidRecipe() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);
        AppUser user = getSampleUser();

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");

        Recipe recipe = new Recipe(1L, "Test name", user, 15, rating, "Test description", null, nutrients, Diet.VEGETARIAN, steps);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipe;
    }

    private static RecipePreviewDTO getValidRecipePreviewDTO() {
        return new RecipePreviewDTO("Test name", Diet.MEAT, 15, 4.5);
    }

    private static RecipeView getValidRecipeView() {
        return new RecipeView() {
            @Override
            public Long getId() {
                return 1L;
            }

            @Override
            public String getName() {
                return "Test name";
            }

            @Override
            public String getDescription() {
                return "Test Description";
            }

            @Override
            public int getPreparationTime() {
                return 75;
            }

            @Override
            public String getUsername() {
                return "admin";
            }

            @Override
            public double getAverageRating() {
                return 4.5;
            }

            @Override
            public int getNumberOfRatings() {
                return 2;
            }

            @Override
            public Diet getDiet() {
                return Diet.VEGAN;
            }

            @Override
            public Nutrients getNutrients() {
                return new Nutrients(1L, 43, 55, 22, 24);
            }

            @Override
            public List<IngredientsView> getIngredients() {
                IngredientsView ingredient1 = new IngredientsView() {

                    @Override
                    public Double getAmount() {
                        return 200D;
                    }

                    @Override
                    public String getName() {
                        return "Ingredient name 1";
                    }

                    @Override
                    public String getUnit() {
                        return "g";
                    }
                };

                IngredientsView ingredient2 = new IngredientsView() {

                    @Override
                    public Double getAmount() {
                        return 2D;
                    }

                    @Override
                    public String getName() {
                        return "Ingredient name 2";
                    }

                    @Override
                    public String getUnit() {
                        return null;
                    }
                };

                List<IngredientsView> ingredients = new ArrayList<>();
                ingredients.add(ingredient1);
                ingredients.add(ingredient2);

                return ingredients;
            }

            @Override
            public List<String> getSteps() {
                List<String> steps = new ArrayList<>();
                steps.add("Wlej mleko do miski");
                steps.add("Wbij jajko do miski");
                steps.add("Wymieszaj");
                steps.add("Upiecz");

                return steps;
            }
        };
    }

//    GET RECIPE

    @Test
    void should_returnForbiddenStatus_when_gettingRecipeWithoutAToken() throws Exception {
        //        given
        RecipeView recipe = getValidRecipeView();
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
        RecipeView recipe = getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.of(recipe));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_returnRecipe_when_recipeHasBeenFound() throws Exception {
        //        given
        RecipeView recipe = getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.of(recipe));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipeViewJacksonTester.write(recipe).getJson());
    }

    @Test
    void should_returnNotFoundStatus_when_recipeHasNotBeenFound() throws Exception {
        //        given
        RecipeView recipe = getValidRecipeView();
        given(recipeRepo.findById(recipe.getId(), RecipeView.class)).willReturn(Optional.empty());

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes/" + recipe.getId())
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

//    ADD RECIPE

    @Test
    void should_returnForbiddenStatus_when_addingRecipeWithoutAToken() throws Exception {
        //        given
        RecipeDTO recipe = getValidRecipeDTO();

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
        RecipeDTO recipe = getValidRecipeDTO();

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/recipes")
                        .characterEncoding("utf-8")
                        .content(recipeDTOJacksonTester.write(recipe).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void should_returnNotFoundStatus_when_addingRecipeByUnknownUser() throws Exception {
        //        given
        RecipeDTO recipe = getValidRecipeDTO();
        doThrow(UserDoesNotExistException.class).when(recipeService).addRecipe(recipe);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/recipes")
                        .characterEncoding("utf-8")
                        .content(recipeDTOJacksonTester.write(recipe).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void should_returnConflictStatus_when_addingRecipeThatAlreadyExit() throws Exception {
        //        given
        RecipeDTO recipe = getValidRecipeDTO();
        doThrow(RecipeAlreadyExistException.class).when(recipeService).addRecipe(recipe);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/recipes")
                        .characterEncoding("utf-8")
                        .content(recipeDTOJacksonTester.write(recipe).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void should_executeAddRecipe_when_addingRecipe() throws Exception {
        //        given
        RecipeDTO recipeDTO = getValidRecipeDTO();
        given(recipeService.addRecipe(recipeDTO)).willReturn(recipeDTO);

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/recipes")
                        .characterEncoding("utf-8")
                        .content(recipeDTOJacksonTester.write(recipeDTO).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        RecipeDTO output = new Gson().fromJson(response.getContentAsString(), RecipeDTO.class);
        verify(recipeService, times(1)).addRecipe(output);
    }

//   GET RECIPE PREVIEWS

    @Test
    void should_returnForbiddenStatus_when_gettingRecipePreviewsWithoutAToken() throws Exception {
        //        given
        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes"))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void should_returnOkStatus_when_returningRecipePreviews() throws Exception {
        //        given
        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes")
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void should_returnEmptyRecipePreviewsList_when_repositoryReturnsNothing() throws Exception {
        //        given
        given(recipeRepo.findAllRecipePreviews(PageRequest.of(0, 10))).willReturn(Collections.emptyList());

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes" + "?page=1&size=10")
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipePreviewListJacksonTester.write(Collections.emptyList()).getJson());
    }

    @Test
    void should_listOfRecipePreviews_when_repositoryReturnsFilledList() throws Exception {
        //        given
        given(recipeRepo.findAllRecipePreviews(PageRequest.of(1, 10))).willReturn(List.of(getValidRecipePreviewDTO()));

        //        when
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/recipes" + "?page=1&size=10")
                        .with(SecurityMockMvcRequestPostProcessors.user(getSampleUser())))
                .andReturn().getResponse();

        //        then
        assertThat(response.getContentAsString()).isEqualTo(recipePreviewListJacksonTester.write(List.of(getValidRecipePreviewDTO())).getJson());
    }

//    EDIT RECIPE

}