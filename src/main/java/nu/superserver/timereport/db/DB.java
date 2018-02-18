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
        System.out.println("Reading " + filename);
        try (FileReader reader = new FileReader(filename)) {
            readWorkdays = new GsonBuilder().create().fromJson(reader,
                    new TypeToken<List<Workday>>() {
                    }.getType());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file: " + getFilename(month) + " Creating empty month");
            readWorkdays = createDays(month);
        } catch (IOException e){
            System.out.println("Failed to read file: " + filename);
            e.printStackTrace();
            readWorkdays = createDays(month);
        }
        return readWorkdays;
    }

    private static List<Workday> createDays(Month month) {
        LocalDate today = LocalDate.of(month.getYear(), month.getMonth(), 1);
        List<Workday> days = new ArrayList<>();
        for (int i = 1; i <= today.lengthOfMonth(); i++) {
            LocalDate day = LocalDate.now().withDayOfMonth(i);
            days.add(new Workday(day));
        }
        return days;
    }

    public static void save(Month currentMonth, List<Workday> days) {
        String filename = getFilename(currentMonth);
        System.out.println("Saving " + filename);
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
