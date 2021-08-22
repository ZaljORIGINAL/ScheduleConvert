package Schedule;

import Database.Row;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupSchedule {
    private final int number;
    private final Map<Short, DayOfWeek> days;
    private final TimeSchedule timeSchedule;

    public GroupSchedule(int number,TimeSchedule timeSchedule) {
        this.number = number;
        days = new HashMap<>();
        this.timeSchedule = timeSchedule;
    }

    public int getNumber() {
        return number;
    }

    public DayOfWeek getDay(short dayIdentify) {
        return days.get(dayIdentify);
    }

    public void setDay(short dayIdentify, DayOfWeek day) {
        days.put(dayIdentify, day);
    }

    public void setDay(short dayIdentify, List<Row> disciplines) {
        days.put(dayIdentify, new DayOfWeek(dayIdentify, disciplines));
    }

    public void save() throws IOException {
        Path convertDir = Paths.get("converted");
        if (!Files.exists(convertDir))
            Files.createDirectory(convertDir);

        days.values()
                .forEach(day -> day.save(
                        Paths.get(String.valueOf(convertDir), "_" + number)
                ));
    }
}
