package Database;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class TablesManagerTest {
    @Test
    public void ManagerCreateTest() {
        Path dbConfig = Paths.get("config", "dev","dbConfig.properties");
        TablesManager manager = new TablesManager(dbConfig);
        assertEquals(2, manager.getTables().size());
    }
}