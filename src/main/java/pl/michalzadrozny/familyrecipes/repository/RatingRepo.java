package pl.michalzadrozny.familyrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.familyrecipes.model.entity.Rating;

@Repository
public interface RatingRepo extends JpaRepository<Rating,Long> {
}
