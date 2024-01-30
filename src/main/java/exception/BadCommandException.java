package exception;

public class BadCommandException extends Exception {
    public BadCommandException() {
        super("Malformed command sent by the player.");
    }

    public BadCommandException(String msg) {
        super(msg);
    }
}
