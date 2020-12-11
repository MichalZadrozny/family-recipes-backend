package pl.michalzadrozny.familyrecipes.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("Użytkownik o podanym mailu już istnieje");
    }
}