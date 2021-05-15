package pl.michalzadrozny.familyrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.michalzadrozny.familyrecipes.model.entity.RecoveryToken;

import java.util.Optional;

@Repository
public interface RecoveryTokenRepo extends JpaRepository<RecoveryToken, Long> {
    Optional<RecoveryToken> findByValue(String value);
}