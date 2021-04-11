package pl.michalzadrozny.familyrecipes.exception;
public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException() {
        super("Internal server error");
    }
}
