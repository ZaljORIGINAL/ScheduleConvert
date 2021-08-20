import excelReader.ExcelReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {
        Path excelSchedule = Paths.get(
                Main.class.getResource("Osen_IS_2020-2021.xls")
                        .toURI());

        ExcelReader reader = new ExcelReader(excelSchedule);
    }
}
