package project.reimbursement.models;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import project.reimbursement.Rates;
import project.reimbursement.enums.CityType;

public class SetTest {

    private Rates rates;

    @Before
    public void buildRates() {
        this.rates = new Rates();
        this.rates.setExampleRates();
    }

    @Test
    public void testAddProject() throws ParseException {
        Set set = new Set(this.rates);
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

        set.addProject(project1);
        assertEquals(project1, set.getProjects().get(0));
    }

    @Test
    public void testGenerateTimeline() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

        Set set = new Set(rates);
        set.addProject(project1);

        set.generateTimeline();

        assertEquals(set.getAllDays().size(), 3);
    }

    @Test
    public void testCalculateTotalInCentsScenario1() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

        Set set = new Set(rates);
        set.addProject(project1);

        set.generateTimeline();

        Integer costInCents = set.calculateTotalInCents();

        assertEquals(16500, costInCents.intValue());
    }

    @Test
    public void testCalculateTotalInCentsScenario2() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "1-Sep-2015";
        String project2Start = "2-Sep-2015";
        String project2End = "6-Sep-2015";
        String project3Start = "6-Sep-2015";
        String project3End = "8-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);
        Project project2 = new Project(project2Start, project2End, CityType.HIGH_COST);
        Project project3 = new Project(project3Start, project3End, CityType.LOW_COST);

        Set set = new Set(rates);
        set.addProject(project1);
        set.addProject(project2);
        set.addProject(project3);

        set.generateTimeline();

        Integer costInCents = set.calculateTotalInCents();
        assertEquals(59000, costInCents.intValue());
    }

    @Test
    public void testCalculateTotalInCentsScenario3() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";
        String project2Start = "5-Sep-2015";
        String project2End = "7-Sep-2015";
        String project3Start = "8-Sep-2015";
        String project3End = "8-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);
        Project project2 = new Project(project2Start, project2End, CityType.HIGH_COST);
        Project project3 = new Project(project3Start, project3End, CityType.HIGH_COST);

        Set set3 = new Set(rates);
        set3.addProject(project1);
        set3.addProject(project2);
        set3.addProject(project3);

        set3.generateTimeline();

        Integer costInCents = set3.calculateTotalInCents();
        assertEquals(44500, costInCents.intValue());
    }

    @Test
    public void testCalculateTotalInCentsScenario4() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "1-Sep-2015";
        String project2Start = "1-Sep-2015";
        String project2End = "1-Sep-2015";
        String project3Start = "2-Sep-2015";
        String project3End = "2-Sep-2015";
        String project4Start = "2-Sep-2015";
        String project4End = "3-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);
        Project project2 = new Project(project2Start, project2End, CityType.LOW_COST);
        Project project3 = new Project(project3Start, project3End, CityType.HIGH_COST);
        Project project4 = new Project(project4Start, project4End, CityType.HIGH_COST);

        Set set = new Set(rates);
        set.addProject(project1);
        set.addProject(project2);
        set.addProject(project3);
        set.addProject(project4);

        set.generateTimeline();

        Integer costInCents = set.calculateTotalInCents();
        assertEquals(18500, costInCents.intValue());
    }
}
