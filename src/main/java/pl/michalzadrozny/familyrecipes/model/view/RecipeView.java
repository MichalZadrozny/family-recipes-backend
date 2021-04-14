package pl.michalzadrozny.familyrecipes.model.view;

import org.springframework.beans.factory.annotation.Value;
import pl.michalzadrozny.familyrecipes.model.entity.Diet;
import pl.michalzadrozny.familyrecipes.model.entity.Nutrients;

import java.util.List;

public interface RecipeView {

    Long getId();

    String getName();

    String getDescription();

    int getPreparationTime();

    @Value("#{target.author.username}")
    String getUsername();

    @Value("#{target.rating.averageRating}")
    double getAverageRating();

    @Value("#{target.rating.ratingsMap.size()}")
    int getNumberOfRatings();

    Diet getDiet();

    Nutrients getNutrients();

    List<IngredientsView> getIngredients();

    List<String> getSteps();

    String getImageName();
}