package exceptions;

public class StudentNotEnrolledException extends Exception {

    public StudentNotEnrolledException() {
    }

    public StudentNotEnrolledException(String msg) {
        super(msg);
    }
}
