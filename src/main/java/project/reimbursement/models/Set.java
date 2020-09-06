package project.reimbursement.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import project.reimbursement.Rates;
import project.reimbursement.enums.CityType;
import project.reimbursement.enums.DayType;
import project.reimbursement.exceptions.ConfigurationException;
import project.reimbursement.exceptions.ProcessException;

/**
 * A set of projects and the capability to construct a consolidated timeline of those projects
 *
 */
public class Set {
    private static final Logger LOGGER = LogManager.getLogger(Set.class);
    private List<Project> projects;
    private Map<Date, Day> allDays;
    private Integer setId;
    private Rates rates;

    public Set() {
        LOGGER.debug("Generating Set");
        projects = new ArrayList<>();
        allDays = new LinkedHashMap<>();
    }

    public Set(Rates rates) {
        this();
        this.rates = rates;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public Map<Date, Day> getAllDays() {
        return Collections.unmodifiableMap(allDays);
    }

    public Integer getSetId() {
        return setId;
    }

    public void setSetId(Integer setId) {
        this.setId = setId;
    }

    /**
     * Construct a single timeline based on the project dates
     * 
     * If two projects have the same day/date, the HIGH_COST prevails.
     */
    public void generateTimeline() {
        if (projects.isEmpty()) {
            throw new ProcessException("Error: No projects to use for timeline generation");
        }
        LOGGER.debug("Beginning Get Calculated Total...");
        allDays = new LinkedHashMap<>();
        this.getProjects().stream().forEach(project -> {
            LOGGER.debug("Processing project " + project);
            Map<Date, Day> days = project.getDays();
            days.values().forEach(day -> {
                Date dayKey = day.getDate();
                if (allDays.containsKey(dayKey)) {
                    Day existingDay = allDays.get(dayKey);
                    if (CityType.LOW_COST.equals(existingDay.getType())) {
                        if (CityType.HIGH_COST.equals(day.getType())) {
                            LOGGER.debug("Replacing low cost day with high cost day");
                            allDays.remove(dayKey);
                            allDays.put(dayKey, day);
                        }
                    }
                } else {
                    allDays.put(day.getDate(), day);
                }
            });
        });

        LOGGER.debug("Generated Timeline:");
        allDays.values().stream().map(day -> day.toString()).forEach(LOGGER::debug);

        processTimeLine();
    }

    /**
     * Process the timeline by generating links for each "Day" node
     * 
     * @throws ProcessException
     */
    private void processTimeLine() throws ProcessException {
        if (allDays.isEmpty()) {
            throw new ProcessException("Error: No Days to process.  Consider running generateTimeLine if projects have already been added to the set");
        }

        Iterator<Map.Entry<Date, Day>> itr = allDays.entrySet().iterator();

        Day lastDay = itr.next().getValue();

        while (itr.hasNext()) {
            Day currentDay = itr.next().getValue();
            Date lastDate = lastDay.getDate();

            Calendar c = Calendar.getInstance();
            c.setTime(lastDate);
            c.add(Calendar.DAY_OF_MONTH, 1);

            // only linking the days together if current day is the day after the last day
            if (c.getTime().equals(currentDay.getDate())) {
                lastDay.setNextDay(currentDay);
                currentDay.setPreviousDay(lastDay);
            }

            lastDay = currentDay;
        }

        LOGGER.debug("Processed Timeline:");
        allDays.values().stream().map(day -> day.toString()).forEach(LOGGER::debug);

    }

    /**
     * Traverse the constructed timeline and determine the value of each day based on its links
     * 
     * @return Total (in cents) of the reimbursement owed for the set.
     * @throws ConfigurationException If a rate is configured incorrectly
     * @throws ProcessException If the set is not in a state where it can calculate the total
     */
    public Integer calculateTotalInCents() throws ConfigurationException, ProcessException {
        checkStatus();
        LOGGER.debug("Calculating Totals:");
        return allDays.values().stream()
                .mapToInt(day -> {
                    if (day.getNextDay().isPresent() && day.getPreviousDay().isPresent()) {
                        Integer rate = rates.getRateInCents(day.getType(), DayType.FULL).getCostInCents();
                        LOGGER.debug("Full Day (Rate: {}) - {}", rate, day);
                        return rate;
                    } else if (day.getNextDay().isPresent() ^ day.getPreviousDay().isPresent()) {
                        Integer rate = rates.getRateInCents(day.getType(), DayType.TRAVEL).getCostInCents();
                        LOGGER.debug("Travel Day (Rate: {}) - {}", rate, day);
                        return rate;
                    } else {
                        return 0;
                    }
                })
                .sum();
    }

    private void checkStatus() throws ProcessException {
        if (projects.isEmpty()) {
            throw new ProcessException("Error: Please add projects to the set and run `generateTimeline()` before you attempt to process the set");
        }
        if (allDays.isEmpty()) {
            throw new ProcessException("Error: Please run `generateTimeline()` before you attempt to process it");
        }
    }
}
