package pl.michalzadrozny.familyrecipes.model.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.entity.AppUser;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;

public class RecipeMapper {
    private RecipeMapper() {
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

    public static ModelMapper recipeToRecipeDTOMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Recipe, RecipeDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDescription(source.getDescription());
                map().setName(source.getName());
                map().setRating(source.getRating());
                map().setNutrients(source.getNutrients());
                map().setDiet(source.getDiet());
                map().setIngredients(source.getIngredients());
                map().setUsername(source.getAuthor().getUsername());
                map().setPreparationTime(source.getPreparationTime());
            }
        });
        return modelMapper;
    }
}