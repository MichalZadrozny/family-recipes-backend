package pl.michalzadrozny.familyrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.familyrecipes.model.entity.Nutrients;

@Repository
public interface NutrientsRepo extends JpaRepository<Nutrients, Long> {
}
