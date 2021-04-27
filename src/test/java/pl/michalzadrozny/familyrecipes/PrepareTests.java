package pl.michalzadrozny.familyrecipes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.michalzadrozny.familyrecipes.model.dto.AddRecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipeDTO;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.entity.*;
import pl.michalzadrozny.familyrecipes.model.view.IngredientsView;
import pl.michalzadrozny.familyrecipes.model.view.RecipeView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class PrepareTests {

    public static AppUser getSampleUser() {
        return new AppUser(1L, "testUser", "312345aD@", "testemail@email.com", true);
    }

    public static RecipeDTO getValidRecipeDTO() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");

        AppUser user = getSampleUser();

        RecipeDTO recipeDTO = new RecipeDTO(1L, "Test name", user.getUsername(), 15, 4.5, 2, "Test description", null, nutrients, Diet.VEGETARIAN, steps, null);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipeDTO.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipeDTO;
    }

    public static AddRecipeDTO getValidAddRecipeDTO() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");
        AppUser user = getSampleUser();

        AddRecipeDTO addRecipeDTO = new AddRecipeDTO("Test name", user.getUsername(), 15, "Test description", null, nutrients, Diet.VEGETARIAN, steps);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        addRecipeDTO.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return addRecipeDTO;
    }

    public static Recipe getValidRecipe() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);
        AppUser user = getSampleUser();

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");

        Recipe recipe = new Recipe(1L, "Test name", user, 15, rating, "Test description", null, nutrients, Diet.VEGETARIAN, steps, null);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipe;
    }

    public static Recipe getValidRecipeWithoutId() {
        Rating rating = new Rating();
        Nutrients nutrients = new Nutrients(1L, 2, 3, 4, 5);
        AppUser user = getSampleUser();

        List<String> steps = new ArrayList<>();
        steps.add("Wlej mleko do miski");
        steps.add("Wbij jajko do miski");
        steps.add("Wymieszaj");
        steps.add("Upiecz");

        Recipe recipe = new Recipe(null, "Test name", user, 15, rating, "Test description", null, nutrients, Diet.VEGETARIAN, steps, null);

        Ingredient ingredient1 = new Ingredient(1L, 100, "ml", "mleka");
        Ingredient ingredient2 = new Ingredient(2L, 2, null, "jajka");

        recipe.setIngredients(Arrays.asList(ingredient1, ingredient2));

        return recipe;
    }

    public static RecipeView getValidRecipeView() {
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

            public String getImageName(){
                return "1.jpg";
            }
        };
    }

    public static RecipePreviewDTO getValidRecipePreviewDTO() {
        return new RecipePreviewDTO(1, "Test name", Diet.MEAT, 15, 4.5, "1.jpg");
    }

    public static MultipartFile getMultipartFile() {
        return new MockMultipartFile("Test multipart file", new byte[4]);
    }

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        try {
            mapper.writeValue(os, obj);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }

        return os.toByteArray();
    }
}
