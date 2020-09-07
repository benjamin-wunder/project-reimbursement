package project.reimbursement;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import project.reimbursement.enums.CityType;
import project.reimbursement.models.Project;
import project.reimbursement.models.Set;

public class App {

    private static final int CENTS_IN_A_DOLLAR = 100;

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) throws ParseException, JsonParseException, JsonMappingException, IOException {

        Rates rates = new Rates();
        rates.setExampleRates();
        createSet1(rates);
        LOGGER.info("---------");
        createSet2(rates);
        LOGGER.info("---------");
        createSet3(rates);
        LOGGER.info("---------");
        createSet4(rates);
        LOGGER.info("---------");
        createSet5(rates);

        /*List<String> argumentArray = Arrays.asList(args);
        argumentArray.forEach(System.out::println);
        SetInput setInput = importFrom("c:\\temp\\reimbursement_input.json");
        
        LOGGER.debug("Set Size: {}", setInput.getSets().size());
        
        setInput.getSets().stream().forEach(set -> {
            set.generateTimeline();
            Integer costInCents = set.calculateTotalInCents();
            LOGGER.info("Total for set {}: ${}", set.getSetId(), costInCents / CENTS_IN_A_DOLLAR);
        });*/
    }

    public static void createSet1(Rates rates) throws ParseException {
        try {
            String project1Start = "1-Sep-2015";
            String project1End = "3-Sep-2015";

            Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

            Set set = new Set(rates);
            set.addProject(project1);

            set.generateTimeline();

            Integer costInCents = set.calculateTotalInCents();
            LOGGER.info("Total for set 1: ${}", costInCents / CENTS_IN_A_DOLLAR);
        } catch (Exception e) {
            LOGGER.error("Exception caught for set1: " + e.getClass() + " - " + e.getMessage());
        }
    }

    public static void createSet2(Rates rates) throws ParseException {
        try {
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

            LOGGER.info("Total for set 2: $" + set.calculateTotalInCents() / CENTS_IN_A_DOLLAR);
        } catch (Exception e) {
            LOGGER.error("Exception caught for set2: " + e.getClass() + " - " + e.getMessage());
        }
    }

    public static void createSet3(Rates rates) throws ParseException {
        try {
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

            LOGGER.info("Total for set 3: $" + set3.calculateTotalInCents() / CENTS_IN_A_DOLLAR);
        } catch (Exception e) {
            LOGGER.error("Exception caught for set3: " + e.getClass() + " - " + e.getMessage());
        }
    }

    public static void createSet4(Rates rates) throws ParseException {
        try {
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

            LOGGER.info("Total for set 4: $" + set.calculateTotalInCents() / CENTS_IN_A_DOLLAR);
        } catch (Exception e) {
            LOGGER.error("Exception caught for set4: " + e.getClass() + " - " + e.getMessage());
        }
    }

    public static void createSet5(Rates rates) throws ParseException {
        try {
            String project1Start = "3-Sep-2015";
            String project1End = "1-Sep-2015";

            Project project1 = new Project(project1Start, project1End, CityType.LOW_COST);

            Set set = new Set(rates);
            set.addProject(project1);

            set.generateTimeline();

            LOGGER.info("Total for set 4: $" + set.calculateTotalInCents() / CENTS_IN_A_DOLLAR);
        } catch (Exception e) {
            LOGGER.error("Exception caught for set5: " + e.getClass() + " - " + e.getMessage());
        }
    }

    public static SetInput importFrom(String fileName) throws JsonParseException, JsonMappingException, IOException {

        File jsonFile = new File(fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        mapper.setDateFormat(formatter);
        SetInput setInput = mapper.readValue(jsonFile, SetInput.class);

        setInput.getSets().stream().forEach(set -> {
            set.setRates(setInput.getRates());
        });

        return setInput;

    }

}