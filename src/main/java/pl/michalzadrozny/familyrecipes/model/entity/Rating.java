package pl.michalzadrozny.familyrecipes.model.entity;

import lombok.Data;
import pl.michalzadrozny.familyrecipes.exception.IncorrectRatingException;

import java.util.HashMap;

@Data
public class Rating {

    double averageRating;
    HashMap<Long, Integer> ratingsMap;

    public Rating() {
        this.averageRating = 0;
        this.ratingsMap = new HashMap<>();
    }

    public void addRating(AppUser user, int rating) {
        if (rating > 5 || rating < 1) {
            throw new IncorrectRatingException();
        }

        ratingsMap.put(user.getId(), rating);
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
