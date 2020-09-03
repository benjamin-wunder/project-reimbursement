package project.reimbursement;

import java.text.ParseException;

import project.reimbursement.models.Project;
import project.reimbursement.models.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) throws ParseException {
        createSet1();
        LOGGER.info("---------");
        createSet2();
        LOGGER.info("---------");
        createSet3();
        LOGGER.info("---------");
        createSet4();
        LOGGER.info("---------");
        createSet5();
    }

    public static void createSet1() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

        Set set = new Set();
        set.addProject(project1);

        set.generateTimeline();
        set.processTimeLine();
        LOGGER.info("Total for set 1: $" + set.calculateTotal() / 100);
    }

    public static void createSet2() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "1-Sep-2015";
        String project2Start = "2-Sep-2015";
        String project2End = "6-Sep-2015";
        String project3Start = "6-Sep-2015";
        String project3End = "8-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);
        Project project2 = new Project(project2Start, project2End, CityType.HIGH_COST);
        Project project3 = new Project(project3Start, project3End, CityType.LOW_COST);

        Set set = new Set();
        set.addProject(project1);
        set.addProject(project2);
        set.addProject(project3);

        set.generateTimeline();

        set.processTimeLine();

        LOGGER.info("Total for set 2: $" + set.calculateTotal() / 100);

    }

    public static void createSet3() throws ParseException {
        String project1Start = "1-Sep-2015";
        String project1End = "3-Sep-2015";
        String project2Start = "5-Sep-2015";
        String project2End = "7-Sep-2015";
        String project3Start = "8-Sep-2015";
        String project3End = "8-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);
        Project project2 = new Project(project2Start, project2End, CityType.HIGH_COST);
        Project project3 = new Project(project3Start, project3End, CityType.HIGH_COST);

        Set set3 = new Set();
        set3.addProject(project1);
        set3.addProject(project2);
        set3.addProject(project3);

        set3.generateTimeline();

        set3.processTimeLine();

        LOGGER.info("Total for set 3: $" + set3.calculateTotal() / 100);
    }

    public static void createSet4() throws ParseException {
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

        Set set = new Set();
        set.addProject(project1);
        set.addProject(project2);
        set.addProject(project3);
        set.addProject(project4);

        set.generateTimeline();

        set.processTimeLine();

        LOGGER.info("Total for set 4: $" + set.calculateTotal() / 100);
    }

    public static void createSet5() throws ParseException {
        String project1Start = "3-Sep-2015";
        String project1End = "1-Sep-2015";

        Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

        Set set = new Set();
        set.addProject(project1);

        set.generateTimeline();

        set.processTimeLine();

        LOGGER.info("Total for set 4: $" + set.calculateTotal() / 100);

    }
}