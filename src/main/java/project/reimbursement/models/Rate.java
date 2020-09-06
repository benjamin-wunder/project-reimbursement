package project.reimbursement.models;

import project.reimbursement.enums.CityType;
import project.reimbursement.enums.DayType;

/**
 * Rates - A representation of a single rate
 * 
 * @author benja
 *
 */
public class Rate {
    private CityType cityType;
    private DayType dayType;
    private Integer costInCents;

    public Rate() {
        //For Serialization/Deserialization
    }

    public Rate(CityType cityType, DayType dayType, Integer costInCents) {
        this.cityType = cityType;
        this.dayType = dayType;
        this.costInCents = costInCents;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public DayType getDayType() {
        return dayType;
    }

    public void setDayType(DayType dayType) {
        this.dayType = dayType;
    }

    public Integer getCostInCents() {
        return costInCents;
    }

    public void setCostInCents(Integer costInCents) {
        this.costInCents = costInCents;
    }

    @Override
    public String toString() {
        return "City Type: [" + cityType + "] Day Type: [" + dayType + "]" + " Cost: [$" + costInCents / 100 + "]";
    }
}
