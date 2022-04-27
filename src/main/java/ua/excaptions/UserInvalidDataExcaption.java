package ua.excaptions;

public class UserInvalidDataExcaption extends RuntimeException {
    public UserInvalidDataExcaption() {
        super();
    }

    public UserInvalidDataExcaption(String message) {
        super(message);
    }

    public UserInvalidDataExcaption(String message, Throwable cause) {
        super(message, cause);
    }

    public UserInvalidDataExcaption(Throwable cause) {
        super(cause);
    }
}
