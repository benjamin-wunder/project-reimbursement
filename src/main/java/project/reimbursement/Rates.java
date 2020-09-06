package project.reimbursement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import project.reimbursement.enums.CityType;
import project.reimbursement.enums.DayType;
import project.reimbursement.exceptions.ConfigurationException;
import project.reimbursement.exceptions.RateNotFoundException;
import project.reimbursement.models.Rate;

/**
 * This class represents a set of rates for calculation of the project reimbursements
 *
 */
public class Rates {

    private List<Rate> rates;

    public List<Rate> getRates() {
        //even though we have a setter, still return immutable to guarantee that changes are intentional
        return Collections.unmodifiableList(rates);
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public Rates() {
        // For Serialization
    }

    /**
     * Sets the example rates in case
     */
    public void setExampleRates() {
        rates = new ArrayList<>();
        Rate lowTravel = new Rate(CityType.LOW_COST, DayType.TRAVEL, 4500);
        rates.add(lowTravel);
        Rate highTravel = new Rate(CityType.HIGH_COST, DayType.TRAVEL, 5500);
        rates.add(highTravel);
        Rate lowFull = new Rate(CityType.LOW_COST, DayType.FULL, 7500);
        rates.add(lowFull);
        Rate highFull = new Rate(CityType.HIGH_COST, DayType.FULL, 8500);
        rates.add(highFull);

    }

    /**
     * Retrieve a rate for the cityType and dayType specified
     * 
     * @param cityType Which type of city the rate is for
     * @param dayType What type of day the rate is for
     * @return {Rate} the rate object
     * @throws RateNotFoundException If no rate is defined for the given parameters
     * @throws ConfigurationException If multiple rates are defined for the given parameters
     */
    public Rate getRateInCents(CityType cityType, DayType dayType) throws RateNotFoundException, ConfigurationException {
        List<Rate> foundRates = rates.stream()
                .filter(rate -> rate.getCityType().equals(cityType) && rate.getDayType().equals(dayType))
                .collect(Collectors.toList());

        switch (foundRates.size()) {
        case 0:
            throw new RateNotFoundException("Error: Couldn't find rates to match City Type " + cityType + " and Day Type " + dayType);
        case 1:
            return foundRates.get(0);
        default:
            throw new ConfigurationException("Error: Rate structure improperly configured");
        }
    }

}
