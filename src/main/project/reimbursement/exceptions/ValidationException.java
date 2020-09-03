package project.reimbursement.exceptions;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -2515370580463359703L;

    public ValidationException(String msg) {
        super(msg);
    }
}
