
public class SiteNotFoundException extends Exception {

    public SiteNotFoundException(String message) {
        super(message);
    }

    public SiteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
