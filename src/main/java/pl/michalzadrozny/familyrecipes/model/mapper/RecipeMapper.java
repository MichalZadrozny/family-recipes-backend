package pl.michalzadrozny.familyrecipes.model.mapper;

import org.modelmapper.ModelMapper;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.Rating;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;

public class RecipeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private RecipeMapper() {
    }

    public static AddRecipeDTO convertRecipeToAddRecipeDTO(Recipe recipe) {
        AddRecipeDTO outputRecipe = modelMapper.map(recipe, AddRecipeDTO.class);
        outputRecipe.setUsername(recipe.getAuthor().getUsername());
        return outputRecipe;
    }

    public static RecipeDTO convertRecipeToRecipeDTO(Recipe recipe) {
        RecipeDTO outputRecipe = modelMapper.map(recipe, RecipeDTO.class);
        outputRecipe.setUsername(recipe.getAuthor().getUsername());
        outputRecipe.setAverageRating(recipe.getRating().getAverageRating());
        outputRecipe.setNumberOfRatings(recipe.getRating().getRatingsMap().size());
        return outputRecipe;
    }

    public static Recipe convertAddRecipeDTOtoRecipe(AddRecipeDTO addRecipeDTO, AppUser user) {
        Recipe recipe = modelMapper.map(addRecipeDTO, Recipe.class);
        recipe.setAuthor(user);
        recipe.setRating(new Rating());

        return recipe;
    }
}