package org.code;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Task1 {
    private static final Logger logger = Logger.getLogger("Task1");
    private static final String jsonFilePath = "src/main/resources/statuses.json";
    private static final String csvFilePath = "src/main/resources/results.csv";
    public static void main(String[] args) {
        logger.setLevel(Level.OFF); // to switch on logging delete this line
        JSONParser parser = new JSONParser();
        ArrayList<JSONObject> filteredData = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try (FileReader fileReader = new FileReader(jsonFilePath)) {
            logger.info("Open JSON file");
            JSONArray jsonArray = (JSONArray) parser.parse(fileReader);
            // exclude records before july 2017
            // handle date format from json file
            Date borderDate = dateFormat.parse("2017-06-30 23:59:59");
            for (Object item : jsonArray) {
                JSONObject jsonObject = (JSONObject) item;
                Date contactDate = dateFormat.parse(jsonObject.getAsString("kontakt_ts"));
                if (contactDate.after(borderDate)) {
                    filteredData.add(jsonObject);
                }
            }
            logger.info("Filtered data...");
        } catch (IOException | java.text.ParseException | ParseException exception) {
            logger.warning(exception.getMessage());
            exception.printStackTrace();
        }

        // sort data first by 'klient_id', then by 'klient_ts'
        filteredData.sort((o1, o2) -> {
            int idCompare = Integer.compare(o1.getAsNumber("klient_id").intValue(),
                    o2.getAsNumber("klient_id").intValue());

            if (idCompare == 0) {
                try {
                    Date date1 = dateFormat.parse(o1.getAsString("kontakt_ts"));
                    Date date2 = dateFormat.parse(o2.getAsString("kontakt_ts"));
                    return date1.compareTo(date2);
                } catch (java.text.ParseException exception) {
                    logger.warning(exception.getMessage());
                    exception.printStackTrace();
                }
            }
            return idCompare;
        });

        logger.info("Sorted data...");

        // handle csv output
        try (FileWriter fileWriter = new FileWriter(csvFilePath)) {
            logger.info("Open CSV file");
            fileWriter.write("kontakt_id;klient_id;pracownik_id;status;kontakt_ts\n");
            for (JSONObject item : filteredData) {
                fileWriter.write(
                        item.get("kontakt_id") + ";"
                        + item.get("klient_id") + ";"
                        + item.get("pracownik_id") + ";"
                        + item.get("status") + ";"
                        + item.get("kontakt_ts") + "\n");
            }
        } catch (IOException exception) {
            logger.warning(exception.getMessage());
            exception.printStackTrace();
        }
    }
}