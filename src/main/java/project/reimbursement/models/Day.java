package project.reimbursement.models;

import java.util.Date;
import java.util.Optional;

import project.reimbursement.enums.CityType;

/**
 * This represents a single day node
 * 
 * Constructing the days like nodes allows us to create a simple linked list
 * The beginning and end of any linked list will be considered travel days
 * The middle days of any linked list will be considered full days
 * 
 */

public class Day {
    private Date date;
    private CityType type;

    Optional<Day> previousDay = Optional.empty();
    Optional<Day> nextDay = Optional.empty();

    public Day(Date date, CityType type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CityType getType() {
        return type;
    }

    public void setType(CityType type) {
        this.type = type;
    }

    public Optional<Day> getPreviousDay() {
        return previousDay;
    }

    public void setPreviousDay(Day previousDay) {
        this.previousDay = Optional.of(previousDay);
    }

    public Optional<Day> getNextDay() {
        return nextDay;
    }

    public void setNextDay(Day nextDay) {
        this.nextDay = Optional.of(nextDay);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Day)) {
            return false;
        }
        return this.date.equals(((Day) object).getDate());
    }

    @Override
    public int hashCode() {
        return this.date.hashCode();
    }

    @Override
    public String toString() {
        String returnString = "Date: " + this.date.toString() + "\tType: " + this.type;
        if (this.previousDay.isPresent()) {
            returnString += "\tPrevious Date: " + this.previousDay.get().getDate();
        }
        if (this.nextDay.isPresent()) {
            returnString += "\tNext Date: " + this.nextDay.get().getDate();
        }
        return returnString;
    }
}
