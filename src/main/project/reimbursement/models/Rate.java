package project.reimbursement.models;

import project.reimbursement.CityType;
import project.reimbursement.DayType;

/**
 * Rates - A representation of a single rate
 * @author benja
 *
 */
public class Rate {
    private CityType cityType;
    private DayType dayType;
    private Integer cost;

    public Rate(CityType cityType, DayType dayType, Integer cost) {
        this.cityType = cityType;
        this.dayType = dayType;
        this.cost = cost;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
