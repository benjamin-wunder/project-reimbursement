package project.reimbursement.exceptions;

public class RateNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8960126658701939505L;

    public RateNotFoundException(String msg) {
        super(msg);
    }
}
