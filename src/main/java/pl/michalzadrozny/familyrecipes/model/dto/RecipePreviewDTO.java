package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.michalzadrozny.familyrecipes.model.entity.Diet;

@Data
@AllArgsConstructor
public class RecipePreviewDTO {
    private String name;
    private Diet diet;
    private int preparationTime;
}
