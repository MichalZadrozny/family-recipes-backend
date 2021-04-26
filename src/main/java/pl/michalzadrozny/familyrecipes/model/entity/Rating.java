package pl.michalzadrozny.familyrecipes.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.michalzadrozny.familyrecipes.exception.IncorrectRatingException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private Long id;
    private double averageRating;

    @ElementCollection(fetch = FetchType.EAGER)
    Map<Long, Integer> ratingsMap;

    public Rating() {
        this.averageRating = 0;
        this.ratingsMap = new HashMap<>();
    }

    public void addRating(long userId, int rating) throws IncorrectRatingException {
        if (rating > 5 || rating < 1) {
            throw new IncorrectRatingException();
        }

        ratingsMap.put(userId, rating);
        averageRating = calculateAverageRating();
    }

    private double calculateAverageRating() {
        double sum = 0;

        for (int value : ratingsMap.values()) {
            sum += value;
        }

        return sum / ratingsMap.size();
    }
}
