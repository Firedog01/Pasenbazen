package pl.lodz.p.edu.rest.exception;

public class ClientException extends Exception {

    public ClientException(String message) {
        super(message);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}
