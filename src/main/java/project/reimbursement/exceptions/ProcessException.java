package project.reimbursement.exceptions;

public class ProcessException extends RuntimeException {

    private static final long serialVersionUID = -4426353347474472237L;

    public ProcessException(String msg) {
        super(msg);
    }
}
