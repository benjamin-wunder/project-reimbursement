package project.reimbursement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import project.reimbursement.enums.CityType;
import project.reimbursement.enums.DayType;
import project.reimbursement.exceptions.ConfigurationException;
import project.reimbursement.exceptions.RateNotFoundException;
import project.reimbursement.models.Rate;

/**
 * Rates is a static class that would probably exist as a repository in a production system,
 * with rate structures stored in a RDBMS and accessed through an ORM. For this exercise,
 * I just made them static to keep complexity down.
 *
 */
public class Rates {
    private static List<Rate> rates;

    static {
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
    public static Rate getRate(CityType cityType, DayType dayType) throws RateNotFoundException, ConfigurationException {
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
