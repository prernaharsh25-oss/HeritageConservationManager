/**
 * SiteNotFoundException.java
 *
 * Thrown when a requested heritage site ID does not exist in the database.
 * Checked exception so callers are forced to handle a missing site
 * explicitly (e.g. when recording an inspection or generating a report).
 */
public class SiteNotFoundException extends Exception {

    public SiteNotFoundException(String message) {
        super(message);
    }

    public SiteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
