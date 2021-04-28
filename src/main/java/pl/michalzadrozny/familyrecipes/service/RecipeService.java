package pl.michalzadrozny.familyrecipes.service;

import org.springframework.web.multipart.MultipartFile;
import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;

import java.util.List;

public interface RecipeService {
    RecipeDTO addRecipe(AddRecipeDTO recipeDTO, MultipartFile image) throws RecipeAlreadyExistException, UserDoesNotExistException;

    void editRecipe(RecipeDTO recipeDTO) throws RecipeNotFoundException;

    RecipeDTO addRating(long recipeId, long userId, int newRating);

    List<RecipePreviewDTO> getUserRecipes(long userId);
}