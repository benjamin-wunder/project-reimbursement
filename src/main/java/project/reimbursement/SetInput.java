package project.reimbursement;

import java.util.List;

import project.reimbursement.models.Set;

public class SetInput {

    private List<Set> sets;
    private Rates rates;

    public SetInput() {
        // For serialization/deserialization
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }
}
