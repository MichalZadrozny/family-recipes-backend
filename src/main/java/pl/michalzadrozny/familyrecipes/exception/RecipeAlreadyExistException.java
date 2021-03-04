package pl.michalzadrozny.familyrecipes.exception;

public class RecipeAlreadyExistException extends RuntimeException {
    public RecipeAlreadyExistException(String message) {
        super(message);
    }
}