package pl.michalzadrozny.familyrecipes.model.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.Rating;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;

public class RecipeMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    private RecipeMapper() {
    }

    public static AddRecipeDTO convertRecipeToAddRecipeDTO(Recipe recipe){
        AddRecipeDTO outputRecipe = modelMapper.map(recipe, AddRecipeDTO.class);
        outputRecipe.setUsername(recipe.getAuthor().getUsername());
        return outputRecipe;
    }

    public static Recipe convertAddRecipeDTOtoRecipe(AddRecipeDTO addRecipeDTO, AppUser user){
        Recipe recipe = modelMapper.map(addRecipeDTO, Recipe.class);
        recipe.setAuthor(user);
        recipe.setRating(new Rating());

        return recipe;
    }

    public static ModelMapper recipeDtoToRecipeMapper(AppUser user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<RecipeDTO, Recipe>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDescription(source.getDescription());
                map().setName(source.getName());
                map().setRating(source.getRating());
                map().setNutrients(source.getNutrients());
                map().setDiet(source.getDiet());
                map().setIngredients(source.getIngredients());
                map().setAuthor(user);
                map().setPreparationTime(source.getPreparationTime());
            }
        });
        return modelMapper;
    }
}