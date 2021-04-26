package pl.michalzadrozny.familyrecipes.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michalzadrozny.familyrecipes.exception.IncorrectRatingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RatingTest {

    private Rating rating;

    @BeforeEach
    void initiateRating() {
        rating = new Rating();
    }

    @Test
    void should_overwritePreviousRating_when_userVotesAgain() {
        //        given
        rating.addRating(1, 4);
        rating.addRating(1, 3);

        //        when
        //        then
        assertThat(rating.ratingsMap).containsEntry(1L, 3).doesNotContainEntry(1L, 4);
    }

    @Test
    void should_throwIncorrectRatingException_when_ratingIsLessThan1() {
        //        given
        //        when
        //        then
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(1, 0));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(2, -1));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(3, Integer.MIN_VALUE));
    }

    @Test
    void should_throwIncorrectRatingException_when_ratingIsGreaterThan5() {
        //        given
        //        when
        //        then
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(1, 6));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(2, Integer.MAX_VALUE));
    }

    @Test
    void should_notThrowAnException_when_ratingIsBetween1and5() {
        //        given
        //        when
        //        then
        rating.addRating(1, 1);
        rating.addRating(2, 2);
        rating.addRating(3, 3);
        rating.addRating(4, 4);
        rating.addRating(5, 5);
    }

    @Test
    void should_calculateAverageRating_when_addingNewRating() {
        //        given
        //        when
        //        then
        rating.addRating(1, 1);
        assertThat(rating.getAverageRating()).isEqualTo(1);
        rating.addRating(2, 2);
        assertThat(rating.getAverageRating()).isEqualTo(1.5);
        rating.addRating(3, 3);
        assertThat(rating.getAverageRating()).isEqualTo(2);
        rating.addRating(4, 4);
        assertThat(rating.getAverageRating()).isEqualTo(2.5);
        rating.addRating(5, 5);
        assertThat(rating.getAverageRating()).isEqualTo(3);
        rating.addRating(6, 1);
        assertThat(Math.round(rating.getAverageRating() * 10) / 10.0).isEqualTo(2.7);
    }

    @Test
    void should_addRatingToRatingsMap_when_addingNewRating() {
        //        given
        //        when
        rating.addRating(1, 3);

        //        then
        assertThat(rating.ratingsMap).containsEntry(1L, 3);
    }
}