package database;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TablesManagerTest {
    @Test
    public void ManagerCreateTest() {
        Path dbConfig = Paths.get("config", "dev","dbConfig.properties");
        TablesManager manager = new TablesManager(dbConfig);
    }
}