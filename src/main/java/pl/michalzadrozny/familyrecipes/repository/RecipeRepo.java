package pl.michalzadrozny.familyrecipes.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Long> {

    @Query("SELECT new pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO(recipe.id, recipe.name, recipe.diet, recipe.preparationTime, recipe.rating.averageRating, recipe.imageName) " +
            "FROM Recipe recipe ORDER BY recipe.rating.averageRating DESC")
    List<RecipePreviewDTO> findAllRecipePreviews(Pageable pageable);

    <T> Optional<T> findById(Long id, Class<T> type);

    Optional<Recipe> findByAuthorUsernameAndName(String username, String name);

    @Query("SELECT new pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO(recipe.id, recipe.name, recipe.diet, recipe.preparationTime, recipe.rating.averageRating, recipe.imageName) " +
            "FROM Recipe recipe WHERE recipe.author.id = ?1 ORDER BY recipe.rating.averageRating DESC")
    List<RecipePreviewDTO> findAllUserRecipePreviews(long userId);
}
