package project.reimbursement.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.reimbursement.enums.CityType;
import project.reimbursement.exceptions.ValidationException;

/**
 * This is a class designed to define a single project
 *
 */
public class Project {
    private static final Logger LOGGER = LogManager.getLogger(Project.class);
    Integer id;
    Date startDate;
    Date endDate;
    CityType cityType;
    Map<Date, Day> days;

    /**
     * Construct a Project with the given parameters
     * 
     * @param startDate The beginning date of the project
     * @param endDate The end date of the project
     * @param cityType High or Low cost city
     * @throws ParseException If the dates provided aren't parsable
     */
    public Project(String startDate, String endDate, CityType cityType) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        this.id = (int) Math.floor(Math.random() * 100000000);

        this.startDate = formatter.parse(startDate);
        this.endDate = formatter.parse(endDate);

        if (this.endDate.before(this.startDate)) {
            throw new ValidationException("Error: Project end dates must not come before start dates");
        }
        this.cityType = cityType;
        createDayList();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
    }

    public Map<Date, Day> getDays() {
        return days;
    }

    public void setDays(Map<Date, Day> days) {
        this.days = days;
    }

    /**
     * Generate a list of days based on the given start and end date.
     */
    private void createDayList() {
        LOGGER.debug(this.toString() + " Creating Day List...");
        if (this.startDate == null || this.endDate == null || this.cityType == null) {
            throw new RuntimeException(this.toString() + " Error: missing data to create a day list for this project");
        }
        Date currentDate = this.startDate;
        Date endDate = this.endDate;
        this.days = new LinkedHashMap<>();

        Day startDay = new Day(currentDate, this.cityType);
        days.put(startDay.getDate(), startDay);

        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        currentDate = c.getTime();

        while (!currentDate.after(endDate)) {
            Day day = new Day(currentDate, this.cityType);
            days.put(day.getDate(), day);
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = c.getTime();
        }

        days.values().stream()
                .map(day -> day.toString())
                .forEach(LOGGER::debug);
    }

    /**
     * Output Project ID as string
     */
    @Override
    public String toString() {
        return String.format("[Project (id: %s)]", id);
    }
}
