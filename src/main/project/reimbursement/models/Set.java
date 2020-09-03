package project.reimbursement.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import project.reimbursement.CityType;
import project.reimbursement.DayType;
import project.reimbursement.Rates;
import project.reimbursement.exceptions.ConfigurationException;
import project.reimbursement.exceptions.ProcessException;

/**
 * A set of projects and the capability to construct a consolidated timeline of those projects
 *
 */
public class Set {
    List<Project> projects;
    Map<Date, Day> allDays;
    Integer setId;

    public Set() {
        projects = new ArrayList<>();
        allDays = new LinkedHashMap<>();
    }

    public void addProject(Project project) {
        projects.add(project);
    }

    public List<Project> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public Map<Date, Day> getAllDays() {
        return allDays;
    }

    public void setAllDays(Map<Date, Day> allDays) {
        this.allDays = allDays;
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
        System.out.println("Beginning Get Calculated Total...");
        allDays = new LinkedHashMap<>();
        this.getProjects().stream().forEach(project -> {
            System.out.println("Processing project " + project);
            Map<Date, Day> days = project.getDays();
            days.values().forEach(day -> {
                Date dayKey = day.getDate();
                if (allDays.containsKey(dayKey)) {
                    Day existingDay = allDays.get(dayKey);
                    if (CityType.LOW_COST.equals(existingDay.getType())) {
                        if (CityType.HIGH_COST.equals(day.getType())) {
                            System.out.println("Replacing low cost day with high cost day");
                            allDays.remove(dayKey);
                            allDays.put(dayKey, day);
                        }
                    }
                } else {
                    allDays.put(day.getDate(), day);
                }
            });
        });

        System.out.println("Generated Timeline:");
        allDays.values().stream().forEach(System.out::println);
    }

    /**
     * Process the timeline by generating links for each "Day" node
     * 
     * @throws ProcessException
     */
    public void processTimeLine() throws ProcessException {
        checkStatus();

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

        System.out.println("Processed Timeline:");
        allDays.values().stream().forEach(System.out::println);

    }

    /**
     * Traverse the constructed timeline and determine the value of each day based on its links
     * 
     * @return Total (in cents) of the reimbursement owed for the set.
     * @throws ConfigurationException If a rate is configured incorrectly
     * @throws ProcessException If the set is not in a state where it can calculate the total
     */
    public Integer calculateTotal() throws ConfigurationException, ProcessException {
        checkStatus();
        System.out.println("Calculating Totals:");
        return allDays.values().stream().mapToInt(day -> {
            if (day.getNextDay().isPresent() && day.getPreviousDay().isPresent()) {
                Integer rate = Rates.getRate(day.getType(), DayType.FULL).getCost();
                System.out.println(String.format("Full Day (Rate: %d) - %s", rate, day));
                return rate;
            } else if (day.getNextDay().isPresent() ^ day.getPreviousDay().isPresent()) {
                Integer rate = Rates.getRate(day.getType(), DayType.TRAVEL).getCost();
                System.out.println(String.format("Travel Day (Rate: %d) - %s", rate, day));
                return rate;
            } else {
                return 0;
            }
        }).sum();
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
