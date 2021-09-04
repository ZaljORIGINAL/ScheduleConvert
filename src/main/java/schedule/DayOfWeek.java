package schedule;

import database.Row;

import java.nio.file.Path;
import java.util.List;

public class DayOfWeek {
    private final short identify;
    private final List<Row> disciplines;

    public DayOfWeek(short identify, List<Row> disciplines) {
        this.identify = identify;
        this.disciplines = disciplines;
    }

    public short getIdentify() {
        return identify;
    }

    public List<Row> getDisciplines() {
        return disciplines;
    }

    public void save(Path path) {
        //TODO Требуется создать класс котоый будет заниматься сохранением данных
    }
}
