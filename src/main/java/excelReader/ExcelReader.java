package excelReader;

import Database.GroupScheduleTable;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExcelReader {
    /*https://www.viralpatel.net/java-read-write-excel-file-apache-poi/*/
    private final Map<Integer, GroupAddress> groupAddressMap = new HashMap<>();
    private final HSSFWorkbook book;

    public ExcelReader(Path scheduleExel) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(
                new FileInputStream(scheduleExel.toString()));
        book = new HSSFWorkbook(fs);

        extractGroupAddress();
    }

    public List<Integer> getGroupsNumbers() {
        return groupAddressMap.values().stream()
                .map(GroupAddress::getNumber)
                .collect(Collectors.toList());
    }


    public void extractGroupSchedule(int number, GroupScheduleTable table) {
        GroupAddress address = groupAddressMap.get(number);
        //TODO Считывать данные по расписанию группы и записывать в базу данных
    }

    private void extractGroupAddress() {
        final int rowIndex = 0;
        final int columnStep = 10;
        int sheetsCount = book.getNumberOfSheets();

        HSSFSheet sheet;
        HSSFRow row;
        HSSFCell cell;

        for (int sheetIndex = 0; sheetIndex < sheetsCount; sheetIndex++) {
            sheet = book.getSheetAt(sheetIndex);
            row = sheet.getRow(rowIndex);
            for (int columnIndex = 3; columnIndex < row.getLastCellNum(); columnIndex += columnStep) {
                cell = row.getCell(columnIndex);
                String value = cell.getRichStringCellValue().toString();
                int groupNumber = Integer.parseInt(value.substring(0, value.indexOf(" ")));
                GroupAddress address = new GroupAddress(groupNumber,2, columnIndex - 3, sheetIndex);
                groupAddressMap.put(
                        address.getNumber(),
                        address);
            }
        }
    }
}
