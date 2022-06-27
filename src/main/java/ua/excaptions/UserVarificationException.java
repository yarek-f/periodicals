package ua.excaptions;

public class UserVarificationException extends Exception {
    public UserVarificationException() {
        super();
    }

    public UserVarificationException(String message) {
        super(message);
    }

    public UserVarificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserVarificationException(Throwable cause) {
        super(cause);
    }
}
