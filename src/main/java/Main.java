import database.GroupScheduleTable;
import database.TablesManager;
import schedule.TimeSchedule;
import excelReader.ExcelReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        ExcelReader excelReader = buildExcelReader();
        TablesManager tablesManager = buildTablesManager();
        TimeSchedule timeSchedule = buildTimeSchedule();

        List<Integer> groupsNumbers = excelReader.getGroupsNumbers();
        for (int number : groupsNumbers) {
            try {
                GroupScheduleTable table = tablesManager.getTable(number);
                excelReader.extractGroupSchedule(number, timeSchedule,table);
            } catch (SQLException exception) {

            }
        }
    }

    private static TablesManager buildTablesManager() {
        Path dbConfig = Paths.get("config", "prod", "dbConfig.properties");
        return new TablesManager(dbConfig);
    }

    private static ExcelReader buildExcelReader() throws URISyntaxException, IOException {
        Path excelSchedule = Paths.get(
                Main.class.getResource("Osen_IS_2020-2021.xls")
                        .toURI());

        return new ExcelReader(excelSchedule);
    }

    private static TimeSchedule buildTimeSchedule() throws URISyntaxException, IOException {
        Path timesFile = Path.of(Main.class.getResource("/times.txt").toURI());
        return new TimeSchedule(timesFile);
    }
}
