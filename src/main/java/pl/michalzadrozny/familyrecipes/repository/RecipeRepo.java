package pl.michalzadrozny.familyrecipes.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO;
import pl.michalzadrozny.familyrecipes.model.entity.Recipe;

import java.util.List;

@Repository
public interface RecipeRepo extends JpaRepository<Recipe,Long> {

    @Query("SELECT new pl.michalzadrozny.familyrecipes.model.dto.RecipePreviewDTO(r.name, r.vege, r.preparationTime) FROM Recipe r")
    List<RecipePreviewDTO> findAllRecipePreviews(Pageable pageable);
}
