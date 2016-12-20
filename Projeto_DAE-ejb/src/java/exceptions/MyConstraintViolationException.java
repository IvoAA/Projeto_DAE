package exceptions;

public class MyConstraintViolationException extends Exception {

    public MyConstraintViolationException() {
    }

    public MyConstraintViolationException(String msg) {
        super(msg);
    }
}
