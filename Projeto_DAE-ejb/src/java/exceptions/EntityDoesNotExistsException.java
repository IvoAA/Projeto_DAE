package exceptions;

public class EntityDoesNotExistsException extends Exception {

    public EntityDoesNotExistsException() {
    }

    public EntityDoesNotExistsException(String msg) {
        super(msg);
    }
}
