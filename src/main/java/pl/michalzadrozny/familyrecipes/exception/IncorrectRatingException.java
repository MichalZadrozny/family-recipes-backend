package pl.michalzadrozny.familyrecipes.exception;

public class IncorrectRatingException extends RuntimeException {
    public IncorrectRatingException() {
        super("Podana ocena jest niepoprawna");
    }
}
