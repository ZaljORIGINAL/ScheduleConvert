import Database.TablesManager;
import excelReader.ExcelReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        ExcelReader excelReader = buildExcelReader();
        TablesManager tablesManager = buildTablesManager();
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
}
