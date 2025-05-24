package exceptions;

public class MemberExceptions extends Exception{
    public MemberExceptions() {
    }

    public MemberExceptions(String message) {
        super(message);
    }

    public MemberExceptions(String message, Throwable cause) {
        super(message, cause);
    }
}
