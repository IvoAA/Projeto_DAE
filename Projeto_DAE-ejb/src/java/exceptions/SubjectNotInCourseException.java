package exceptions;

public class SubjectNotInCourseException extends Exception {

    public SubjectNotInCourseException() {
    }

    public SubjectNotInCourseException(String msg) {
        super(msg);
    }
}
