package pl.lodz.p.edu.cassandra.exception;

public class EquipmentException extends Exception {
    public EquipmentException(String message) {
        super(message);
    }

    public EquipmentException(Throwable cause) {
        super(cause);
    }
}
