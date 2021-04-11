package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.michalzadrozny.familyrecipes.model.entity.Diet;
import pl.michalzadrozny.familyrecipes.model.entity.Ingredient;
import pl.michalzadrozny.familyrecipes.model.entity.Nutrients;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddRecipeDTO {

    @NotBlank(message = "Nazwa przepisu nie może być pusta")
    private String name;

    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    private String username;
    private int preparationTime; //In minutes

    private String description;

    @Size(min = 1)
    private List<Ingredient> ingredients;

    private Nutrients nutrients;

    @NotNull(message = "Nazwa użytkownika nie może być pusta")
    private Diet diet;

    @Size(min = 1)
    private List<String> steps;
}
