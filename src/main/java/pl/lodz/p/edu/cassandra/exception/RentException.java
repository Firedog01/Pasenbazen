package pl.lodz.p.edu.cassandra.exception;

public class RentException extends Exception {
    public RentException(String message) {
        super(message);
    }

    public RentException(Throwable cause) {
        super(cause);
    }
}
