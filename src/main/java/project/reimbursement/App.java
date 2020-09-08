package project.reimbursement;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import project.reimbursement.utils.Utils;

public class App {

    private static final int CENTS_IN_A_DOLLAR = 100;
    private static final String FILENAME_FLAG = "f";
    private static final String LOGGER_FLAG = "l";

    private static final Logger LOGGER = LogManager.getLogger(App.class);

    public static void main(String[] args) throws java.text.ParseException,
            JsonParseException,
            JsonMappingException,
            IOException,
            org.apache.commons.cli.ParseException {

        Options options = createOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        SetInput setInput;
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                formatter.printHelp("project", options);
                return;
            }

            if (cmd.hasOption(LOGGER_FLAG)) {
                Level level = Level.valueOf(cmd.getOptionValue(LOGGER_FLAG));
                Configurator.setLevel("project.reimbursement", level);
            }

            if (cmd.hasOption(FILENAME_FLAG)) {
                setInput = Utils.importFrom(cmd.getOptionValue(FILENAME_FLAG));
            } else {
                setInput = Utils.importFrom("scenarios/allscenarios.json");
            }

            LOGGER.debug("Set Size: {}", setInput.getSets().size());

            setInput.getSets().stream().forEach(set -> {
                set.generateTimeline();
                Integer costInCents = set.calculateTotalInCents();
                LOGGER.info("Total for set {}: ${}", set.getSetId(), costInCents / CENTS_IN_A_DOLLAR);
            });
        } catch (IllegalArgumentException | MissingArgumentException e) {
            LOGGER.error("Illegal/Missing Argument Detected.  Printing help");
            System.out.println("Error: Bad Argument");
            formatter.printHelp("java -jar project-reimbursement.jar", options);
        } catch (Exception e) {
            LOGGER.error("The System Ended Abnormally: {}", e.getMessage());
        }
    }

    public static Options createOptions() {
        Options options = new Options();
        Option help = new Option("h", "help", false, "print this message");
        options.addOption(help);
        options.addOption(LOGGER_FLAG, "loglevel", true, "Specify a log level");
        options.addOption(FILENAME_FLAG, "filename", true, "Specify a file for input");
        return options;
    }

}