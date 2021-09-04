package excelReader;

import Database.DBContext;
import Database.DBHelper;
import Database.GroupScheduleTable;
import Schedule.TimeSchedule;
import Schedule.TimeScheduleTest;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ExcelReaderTest {

    @Test
    public void getGroupsNumbers() throws URISyntaxException, IOException {
        Path excel = Paths.get(
                ExcelReaderTest.class.getResource("/ScheduleToTest.xls")
                        .toURI());
        ExcelReader reader = new ExcelReader(excel);
        List<Integer> groups = reader.getGroupsNumbers();

        List<Integer> expected = new java.util.ArrayList<>(
                List.of(2201117, 2191117, 2181121, 2171121));

        if (groups.size() != expected.size())
            fail();

        for (Integer integer : groups) {
            expected.removeIf(var -> expected.contains(integer));
        }

        if (expected.size() != 0)
            fail();
    }

    @Test
    public void extractGroupSchedule() throws URISyntaxException, IOException, SQLException {
        Path excel = Paths.get(
                ExcelReaderTest.class.getResource("/ScheduleToTest.xls")
                        .toURI());
        ExcelReader reader = new ExcelReader(excel);

        Path times = Path.of((TimeScheduleTest.class.getResource("/times.txt")).toURI());
        TimeSchedule timeSchedule = new TimeSchedule(times);

        Path dbConfig = Paths.get("config", "dev","dbConfig.properties");
        DBContext context = new DBContext(dbConfig);
        GroupScheduleTable table = new GroupScheduleTable(context.TABLE_NAME_PREFIX + "_" + 2191117, context);

        try {
            reader.extractGroupSchedule(2191117, timeSchedule, table);
        } catch (SQLException exception) {
            table.deleteTable();
            fail();
        }

        try (Connection connection = context.open()) {

            String sqlCommand = "SELECT count(" + DBHelper.COLUMN_DISCIPLINE_NAME + ") AS RowsCount FROM " + table.getName() + ";";
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                try (ResultSet result = statement.executeQuery()) {
                    result.next();
                    if (result.getInt("RowsCount") != 23)
                        fail();
                }
            }
        }
        table.deleteTable();
    }
}