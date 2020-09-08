package project.reimbursement.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import project.reimbursement.SetInput;
import project.reimbursement.models.Project;

public class Utils {

    private static final Logger LOGGER = LogManager.getLogger(Project.class);

    public static SetInput importFrom(String fileName) throws JsonParseException, JsonMappingException, IOException {

        LOGGER.debug("File Name: {}", fileName);
        File jsonFile = new File(fileName);
        InputStream inputStream;

        if (jsonFile.exists()) {
            inputStream = new FileInputStream(jsonFile);
        } else {
            inputStream = Utils.class.getClassLoader().getResourceAsStream(fileName);
        }

        if (inputStream == null) {
            throw new FileNotFoundException("Error: File (" + fileName + ") could not be found");
        }

        try (InputStream anotherStream = inputStream;) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            mapper.setDateFormat(formatter);
            SetInput setInput = mapper.readValue(anotherStream, SetInput.class);

            setInput.getSets().stream().forEach(set -> {
                set.setRates(setInput.getRates());
            });

            return setInput;
        }
    }
}
