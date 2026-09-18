/**
 * InvalidDataException.java
 *
 * Thrown when user input or parsed data fails validation
 * (e.g. blank required field, invalid status/priority value,
 * malformed date, duplicate ID).
 */
public class InvalidDataException extends Exception {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
