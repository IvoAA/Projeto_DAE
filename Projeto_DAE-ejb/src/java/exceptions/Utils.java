package exceptions;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class Utils {

    static public String getConstraintViolationMessages(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        StringBuilder errorMessages = new StringBuilder();
        for (ConstraintViolation<?> cv : cvs) {
            errorMessages.append(cv.getMessage());
            errorMessages.append("; ");
        }
        return errorMessages.toString();
    }
}
