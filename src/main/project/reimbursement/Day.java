package project.reimbursement;

import java.util.Date;
import java.util.Optional;

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
        String returnString = this.date.toString();
        if (this.previousDay.isPresent()) {
            returnString += "\tPrevious Date: " + this.previousDay.get().getDate();
        }
        if (this.nextDay.isPresent()) {
            returnString += "\tNext Date: " + this.nextDay.get().getDate();
        }
        return returnString;
    }
}
