package pl.michalzadrozny.familyrecipes.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Podana nazwa użytkownika jest już zajęta");
    }
}