import excelReader.GroupAddress;

import java.nio.file.Path;
import java.util.Map;

public class ExcelReader {
    /*https://www.viralpatel.net/java-read-write-excel-file-apache-poi/*/
    private final Map<Integer, GroupAddress> groupAddressMap;

    public ExcelReader(Path scheduleExel) {
        groupAddressMap = getGroupAddressMap();
    }

    private Map<Integer, GroupAddress> getGroupAddressMap(){
        return null;
    }
}
