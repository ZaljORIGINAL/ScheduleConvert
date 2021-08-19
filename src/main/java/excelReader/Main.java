package excelReader;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws URISyntaxException {
        Path schedule = Paths.get(
                Main.class.getResource("Osen_IS_2020-2021.xls")
                        .toURI());


    }
}
