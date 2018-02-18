package nu.superserver.timereport.db;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import nu.superserver.timereport.Workday;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static List<Workday> get(Month month) {
        List<Workday> readWorkdays;
        String filename = getFilename(month);
        try (FileReader reader = new FileReader(filename)) {
            readWorkdays = new GsonBuilder().create().fromJson(reader,
                    new TypeToken<List<Workday>>() {
                    }.getType());
        } catch (IOException e) {
            System.out.println("Failed to read file: " + filename);
            e.printStackTrace();
            readWorkdays = createDays();
        }
        return readWorkdays;
    }

    private static List<Workday> createDays() {
        LocalDate today = LocalDate.now();
        List<Workday> days = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            LocalDate day = LocalDate.now().withDayOfMonth(i);
            days.add(new Workday(day));
        }
        return days;
    }

    public static void save(Month currentMonth, List<Workday> days) {
        String filename = getFilename(currentMonth);
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")){
            new GsonBuilder().setPrettyPrinting().create().toJson(days, writer);
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            System.out.println("Failed to Save data: " + days);
            e1.printStackTrace();
        }
    }

    private static String getFilename(Month currentMonth) {
        return currentMonth.getYear() + "_" + currentMonth.getMonth();
    }
}
