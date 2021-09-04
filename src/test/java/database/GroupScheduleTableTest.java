package database;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class GroupScheduleTableTest {
    @Test
    public void GroupScheduleTableConstructorTest() throws SQLException {
        Path dbConfig = Paths.get("config", "dev","dbConfig.properties");
        DBContext context = new DBContext(dbConfig);
        GroupScheduleTable table = new GroupScheduleTable(context.TABLE_NAME_PREFIX + "_" + 2201120, context);
        if (table.existTable())
            table.deleteTable();
        else
            fail();
    }
}