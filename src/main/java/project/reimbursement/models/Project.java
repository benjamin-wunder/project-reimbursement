package project.reimbursement.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.reimbursement.enums.CityType;
import project.reimbursement.exceptions.ProcessException;
import project.reimbursement.exceptions.ValidationException;

/**
 * This is a class designed to define a single project
 *
 */
public class Project {
    private static final Logger LOGGER = LogManager.getLogger(Project.class);
    private Integer id;

    private CityType cityType;
    private Optional<Date> startDate = Optional.empty();
    private Optional<Date> endDate = Optional.empty();
    private Map<Date, Day> days;

    public Project() {
        // For serialization
    }

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

        this.startDate = Optional.of(formatter.parse(startDate));
        this.endDate = Optional.of(formatter.parse(endDate));

        if (this.endDate.get().before(this.startDate.get())) {
            throw new ValidationException("Error: Project end dates must not come before start dates");
        }
        this.cityType = cityType;
        createDayList();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Optional<Date> getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        if (this.startDate.isPresent()) {
            throw new ProcessException("Error: Start Date already set");
        }
        this.startDate = Optional.of(startDate);
        if (this.endDate.isPresent() && this.cityType != null) {
            LOGGER.debug("Creating from setStartDate");
            createDayList();
        }
    }

    public Optional<Date> getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        if (this.endDate.isPresent()) {
            throw new ProcessException("Error: End Date already set");
        }
        this.endDate = Optional.of(endDate);
        if (this.startDate.isPresent() && this.cityType != null) {
            LOGGER.debug("Creating from setEndDate");
            createDayList();
        }
    }

    public CityType getCityType() {
        return cityType;
    }

    public void setCityType(CityType cityType) {
        this.cityType = cityType;
        if (this.startDate != null && this.endDate != null) {
            LOGGER.debug("Creating from city type");
            createDayList();
        }
    }

    public Map<Date, Day> getDays() {
        return Collections.unmodifiableMap(days);
    }

    /**
     * Generate a list of days based on the given start and end date.
     */
    private void createDayList() {
        LOGGER.debug(this.toString() + " Creating Day List...");
        if (this.startDate == null || this.endDate == null || this.cityType == null) {
            throw new ProcessException(this.toString() + " Error: missing data to create a day list for this project");
        }

        // Since the sets are using the project day objects to calculate project timelines, this shouldn't be run twice on the same project
        if (this.days != null && !this.days.isEmpty()) {
            throw new ProcessException(
                    this.toString() + " Error: Project day list already created.  Running again may disrupt set calcualtions already completed");
        }

        Date currentDate = this.startDate.get();
        Date endDate = this.endDate.get();
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
