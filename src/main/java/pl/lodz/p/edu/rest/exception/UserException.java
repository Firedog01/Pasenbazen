package pl.lodz.p.edu.rest.exception;

public class UserException extends Exception {

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
}
