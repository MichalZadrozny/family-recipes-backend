package pl.michalzadrozny.familyrecipes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.*;
import pl.michalzadrozny.familyrecipes.model.mapper.RecipeMapper;
import pl.michalzadrozny.familyrecipes.repository.RecipeRepo;
import pl.michalzadrozny.familyrecipes.repository.UserRepo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    private RecipeRepo recipeRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private static AppUser getSampleUser() {
        return new AppUser(1L, "testUser", "312345aD@", "testemail@email.com", true);
    }

    private static RecipeDTO getValidRecipeDTO() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);

        Map<Long, String> steps = new HashMap<>();
        steps.put(1L, "Wlej mleko do miski");
        steps.put(2L, "Wbij jajko do miski");
        steps.put(3L, "Wymieszaj");
        steps.put(4L, "Upiecz");

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

        Map<Long, String> steps = new HashMap<>();
        steps.put(1L, "Wlej mleko do miski");
        steps.put(2L, "Wbij jajko do miski");
        steps.put(3L, "Wymieszaj");
        steps.put(4L, "Upiecz");

        Recipe recipe = new Recipe(1L, "Test name", user, 15, rating, "Test description", null, nutrients, Diet.VEGETARIAN, steps);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipe;
    }

//    ADD RECIPE

    @Test
    void should_throwRecipeAlreadyExistException_when_recipeHasBeenFound() {
//        given
        Recipe recipe = getValidRecipe();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.of(recipe));
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
//        then
        assertThatExceptionOfType(RecipeAlreadyExistException.class).isThrownBy(() -> recipeService.addRecipe(recipeDTO));
    }

    @Test
    void should_throwUserDoesNotExistException_when_addingRecipeByUnknownUser() {
//        given
        Recipe recipe = getValidRecipe();
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.empty());
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
//        then
        assertThatExceptionOfType(UserDoesNotExistException.class).isThrownBy(() -> recipeService.addRecipe(recipeDTO));
    }

    @Test
    void should_saveRecipe_when_addingRecipe() {
//        given
        Recipe recipe = getValidRecipe();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.empty());
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.of(recipe.getAuthor()));
        given(recipeRepo.save(recipe)).willReturn(recipe);
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
        recipeService.addRecipe(recipeDTO);
//        then
        verify(recipeRepo, times(1)).save(recipe);
    }

//    EDIT RECIPE

    @Test
    void should_throwRecipeNotFoundException_when_recipeHasBeenFound() {
//        given
        Recipe recipe = getValidRecipe();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.of(recipe));
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
//        then
        assertThatExceptionOfType(RecipeNotFoundException.class).isThrownBy(() -> recipeService.editRecipe(recipeDTO));
    }

    @Test
    void should_throwUserDoesNotExistException_when_editingRecipeByUnknownUser() {
//        given
        Recipe recipe = getValidRecipe();
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.empty());
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
//        then
        assertThatExceptionOfType(UserDoesNotExistException.class).isThrownBy(() -> recipeService.addRecipe(recipeDTO));
    }

    @Test
    void should_saveRecipe_when_editingRecipe() {
//        given
        Recipe recipe = getValidRecipe();
        given(recipeRepo.findByAuthorUsernameAndName(recipe.getAuthor().getUsername(), recipe.getName())).willReturn(Optional.empty());
        given(userRepo.findByUsername(recipe.getAuthor().getUsername())).willReturn(Optional.of(recipe.getAuthor()));
        RecipeDTO recipeDTO = RecipeMapper.recipeToRecipeDTOMapper().map(recipe, RecipeDTO.class);

//        when
        recipeService.editRecipe(recipeDTO);

//        then
        verify(recipeRepo, times(1)).save(recipe);
    }
}