package database;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class DBContextTest {
    @Test
    public void configReadTest() {
        Path dbConfig = Paths.get("config", "dev","dbConfig.properties");
        DBContext context = new DBContext(dbConfig);

        assertNotNull(context.DATABASE_URL);
        assertNotNull(context.DATABASE_NAME);
        assertNotNull(context.TABLE_NAME_PREFIX);
        assertNotNull(context.USER_NAME);
        assertNotNull(context.USER_PASSWORD);
    }
}