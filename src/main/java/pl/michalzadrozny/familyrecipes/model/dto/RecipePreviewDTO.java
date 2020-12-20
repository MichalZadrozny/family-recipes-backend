package pl.michalzadrozny.familyrecipes.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.michalzadrozny.familyrecipes.model.entity.Vege;

@Data
@AllArgsConstructor
public class RecipePreviewDTO {
    private String name;
    private Vege vege;
    private int preparationTime;
}
