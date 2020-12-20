package pl.michalzadrozny.familyrecipes.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.michalzadrozny.familyrecipes.exception.IncorrectRatingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RatingTest {

    private Rating rating;

    @BeforeEach
    void initiateRating(){
        rating = new Rating();
    }

    private static AppUser getSampleUser() {
        AppUser user = new AppUser();
        user.setUsername("testUser");
        user.setPassword("312345aD@");
        user.setEmail("testemail@email.com");
        return user;
    }

    @Test
    void should_overwritePreviousRating_when_userVotesAgain() {
        //        given
        rating.addRating(getSampleUser(),4);
        rating.addRating(getSampleUser(),3);

        //        when
        //        then
        assertThat(rating.ratingsMap).containsEntry(getSampleUser().getId(),3);
        assertThat(rating.ratingsMap).doesNotContainEntry(getSampleUser().getId(),4);
    }

    @Test
    void should_throwIncorrectRatingException_when_ratingIsLessThan1() {
        //        given
        //        when
        //        then
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(getSampleUser(),0));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(getSampleUser(),-1));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(getSampleUser(),Integer.MIN_VALUE));
    }

    @Test
    void should_throwIncorrectRatingException_when_ratingIsGreaterThan5() {
        //        given
        //        when
        //        then
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(getSampleUser(),6));
        assertThatExceptionOfType(IncorrectRatingException.class).isThrownBy(() -> rating.addRating(getSampleUser(),Integer.MAX_VALUE));
    }

    @Test
    void should_notThrowAnException_when_ratingIsBetween1and5() {
        //        given
        AppUser user1 = new AppUser(1L,"user","312345aD@","user@email.com",true);
        AppUser user2 = new AppUser(2L,"user","312345aD@","user@email.com",true);
        AppUser user3 = new AppUser(3L,"user","312345aD@","user@email.com",true);
        AppUser user4 = new AppUser(4L,"user","312345aD@","user@email.com",true);
        AppUser user5 = new AppUser(5L,"user","312345aD@","user@email.com",true);

        //        when
        //        then
        rating.addRating(user1,1);
        rating.addRating(user2,2);
        rating.addRating(user3,3);
        rating.addRating(user4,4);
        rating.addRating(user5,5);
    }

    @Test
    void should_calculateAverageRating_when_addingNewRating() {
        //        given
        AppUser user1 = new AppUser(1L,"user","312345aD@","user@email.com",true);
        AppUser user2 = new AppUser(2L,"user","312345aD@","user@email.com",true);
        AppUser user3 = new AppUser(3L,"user","312345aD@","user@email.com",true);
        AppUser user4 = new AppUser(4L,"user","312345aD@","user@email.com",true);
        AppUser user5 = new AppUser(5L,"user","312345aD@","user@email.com",true);
        AppUser user6 = new AppUser(6L,"user","312345aD@","user@email.com",true);


        //        when
        //        then
        rating.addRating(user1,1);
        assertThat(rating.getAverageRating()).isEqualTo(1);
        rating.addRating(user2,2);
        assertThat(rating.getAverageRating()).isEqualTo(1.5);
        rating.addRating(user3,3);
        assertThat(rating.getAverageRating()).isEqualTo(2);
        rating.addRating(user4,4);
        assertThat(rating.getAverageRating()).isEqualTo(2.5);
        rating.addRating(user5,5);
        assertThat(rating.getAverageRating()).isEqualTo(3);
        rating.addRating(user6,1);
        assertThat(Math.round(rating.getAverageRating()*10)/10.0).isEqualTo(2.7);
    }

    @Test
    void should_addRatingToRatingsMap_when_addingNewRating() {
        //        given
        //        when
        rating.addRating(getSampleUser(),3);

        //        then
        assertThat(rating.ratingsMap).containsEntry(getSampleUser().getId(),3);
    }
}