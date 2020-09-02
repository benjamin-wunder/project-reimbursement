package project.reimbursement;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Project {
    Date startDate;
    Date endDate;
    CityType cityType;
    Map<Date, Day> days;

    Project(Date startDate, Date endDate, CityType cityType) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.cityType = cityType;
        createDayList();
    }

    private void createDayList() {
        System.out.println("Creating Day List...");
        if (this.startDate == null || this.endDate == null || this.cityType == null) {
            throw new RuntimeException("Error: missing data to create a day list for this project");
        }
        Date currentDate = this.startDate;
        Date endDate = this.endDate;
        this.days = new LinkedHashMap<>();

        Day startDay = new Day(currentDate, this.cityType);
        days.put(startDay.getDate(), startDay);

        Day previousDay = startDay;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DAY_OF_MONTH, 1);
        currentDate = c.getTime();

        while (!currentDate.after(endDate)) {
            Day day = new Day(currentDate, this.cityType);
            previousDay.setNextDay(day);
            day.setPreviousDay(previousDay);
            days.put(day.getDate(), day);

            previousDay = day;
            c.setTime(currentDate);
            c.add(Calendar.DAY_OF_MONTH, 1);
            currentDate = c.getTime();
        }

        days.values().stream().forEach(day -> {
            System.out.println(day);
        });
    }
}
