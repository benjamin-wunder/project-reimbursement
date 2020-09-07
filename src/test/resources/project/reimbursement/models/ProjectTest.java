package project.reimbursement.models;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import project.reimbursement.enums.CityType;
import project.reimbursement.exceptions.ValidationException;

public class ProjectTest {

    @Test
    public void testProject() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        String startDateString = "1-SEP-2020";
        String endDateString = "2-SEP-2020";
        Project project = new Project(startDateString, endDateString, CityType.LOW_COST);
        Date startDate = formatter.parse(startDateString);
        Date endDate = formatter.parse(endDateString);
        assertEquals(project.getStartDate(), startDate);
        assertEquals(project.getEndDate(), endDate);
        assertEquals(project.getCityType(), CityType.LOW_COST);

    }

    @Test(expected = ParseException.class)
    public void testProjectBadDates() throws ParseException {
        new Project("1-BADDATA", "2-BADDATA", CityType.LOW_COST);
    }

    @Test(expected = ValidationException.class)
    public void testProjectEndDateBeforeStartDate() throws ParseException {
        new Project("4-SEP-2015", "1-SEP-2015", CityType.LOW_COST);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testProjectDaysProtected() throws ParseException {
        Project project = new Project("1-SEP-2020", "2-SEP-2020", CityType.LOW_COST);
        project.getDays().clear();
    }

    @Test
    public void testProjectCreatesDays() throws ParseException {
        String startDateString = "1-SEP-2020";
        String endDateString = "10-SEP-2020";
        Project project = new Project(startDateString, endDateString, CityType.LOW_COST);
        assertEquals(project.getDays().size(), 10);
    }
}
