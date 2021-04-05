package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.michalzadrozny.familyrecipes.model.entity.Diet;

@Data
@AllArgsConstructor
public class RecipePreviewDTO {
    private long id;
    private String name;
    private Diet diet;
    private int preparationTime;
    private double averageRating;
}
