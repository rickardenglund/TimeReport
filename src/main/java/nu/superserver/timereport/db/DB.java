package nu.superserver.timereport.db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nu.superserver.timereport.Month;
import nu.superserver.timereport.Workday;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static Logger LOGGER = LogManager.getLogger(DB.class);
    public static List<Workday> get(Month month) {
        List<Workday> readWorkdays;
        File file = getFile(month);
        LOGGER.info("Reading " + file);
        try (FileReader reader = new FileReader(file)) {
            readWorkdays = new GsonBuilder().create().fromJson(reader,
                    new TypeToken<List<Workday>>() {
                    }.getType());
        } catch (FileNotFoundException e) {
            LOGGER.warn("Unable to find file: " + getFile(month) + " Creating empty month");
            readWorkdays = createDays(month);
        } catch (IOException e){
            LOGGER.error("Failed to read file: " + file.getName(), e);
            readWorkdays = createDays(month);
        }
        return readWorkdays;
    }

    private static List<Workday> createDays(Month month) {
        LocalDate firstDayInMonth = LocalDate.of(month.getYear(), month.getMonth(), 1);
        List<Workday> days = new ArrayList<>();
        for (int i = 1; i <= firstDayInMonth.lengthOfMonth(); i++) {
            LocalDate day = firstDayInMonth.withDayOfMonth(i);
            days.add(new Workday(day));
        }
        return days;
    }

    public static void save(Month currentMonth, List<Workday> days) {
        File file = getFile(currentMonth);
        LOGGER.info("Saving " + file.getName());
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")){
            new GsonBuilder().setPrettyPrinting().create().toJson(days, writer);
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            LOGGER.error("Failed to Save data: " + days, e1);
        }
    }

    private static File getFile(Month currentMonth) {
        File file = new File ("files/" + currentMonth.getYear() + "_" + currentMonth.getMonth() + ".json");
        file.getParentFile().mkdirs();
        return file;
    }
}
