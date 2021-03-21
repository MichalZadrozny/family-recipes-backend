package pl.michalzadrozny.familyrecipes.service;

import pl.michalzadrozny.familyrecipes.exception.RecipeAlreadyExistException;
import pl.michalzadrozny.familyrecipes.exception.RecipeNotFoundException;
import pl.michalzadrozny.familyrecipes.exception.UserDoesNotExistException;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;

public interface RecipeService {
    RecipeDTO addRecipe(RecipeDTO recipeDTO) throws RecipeAlreadyExistException, UserDoesNotExistException;

    void editRecipe(RecipeDTO recipeDTO) throws RecipeNotFoundException;
}