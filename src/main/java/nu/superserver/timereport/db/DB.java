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
        File file = getFile(month);
        System.out.println("Reading " + file);
        try (FileReader reader = new FileReader(file)) {
            readWorkdays = new GsonBuilder().create().fromJson(reader,
                    new TypeToken<List<Workday>>() {
                    }.getType());
        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file: " + getFile(month) + " Creating empty month");
            readWorkdays = createDays(month);
        } catch (IOException e){
            System.out.println("Failed to read file: " + file.getName());
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
        File file = getFile(currentMonth);
        System.out.println("Saving " + file.getName());
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")){
            new GsonBuilder().setPrettyPrinting().create().toJson(days, writer);
        } catch (FileNotFoundException | UnsupportedEncodingException e1) {
            System.out.println("Failed to Save data: " + days);
            e1.printStackTrace();
        }
    }

    private static File getFile(Month currentMonth) {
        File file = new File ("files/" + currentMonth.getYear() + "_" + currentMonth.getMonth() + ".json");
        file.getParentFile().mkdirs();
        return file;
    }
}
