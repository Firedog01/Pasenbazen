package exception;

public class ClientException extends Exception {

    public ClientException(String message) {
        super(message); //Is that enough?
    }

    public ClientException(Throwable cause) {
        super(cause);
    }
}