package pl.lodz.p.edu.rest.exception.user;

public class UserConflictException extends Exception {
    public UserConflictException(String message) {
        super(message);
    }
}
