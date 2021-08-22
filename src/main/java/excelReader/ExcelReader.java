package excelReader;

import Database.DBHelper;
import Database.GroupScheduleTable;
import Schedule.TimeSchedule;
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


    public void extractGroupSchedule(int number, TimeSchedule timeSchedule, GroupScheduleTable table) {
        GroupAddress address = groupAddressMap.get(number);
/*        HSSFRow row = book.getSheetAt(address.getPageIndex()).getRow(address.getRowIndex());
        table.insert(new Row(
                getDayOfWeek(address, row),
                getTime(address, row, timeSchedule)
        ))*/
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

    private short getDayOfWeek(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_DAY_OF_WEEK);
        String value = cell.getStringCellValue();
        return switch (value) {
            case ExcelHelper.MONDAY -> DBHelper.MONDAY;
            case ExcelHelper.TUESDAY -> DBHelper.TUESDAY;
            case ExcelHelper.WEDNESDAY -> DBHelper.WEDNESDAY;
            case ExcelHelper.THURSDAY -> DBHelper.THURSDAY;
            case ExcelHelper.FRIDAY -> DBHelper.FRIDAY;
            case ExcelHelper.SATURDAY -> DBHelper.SATURDAY;
            default -> (short) -1;
        };
    }

    private short getTime(GroupAddress address, HSSFRow row, TimeSchedule timeSchedule) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_TIME);
        String value = cell.getStringCellValue();
        return (short) timeSchedule.getPositionByTime(value);
    }
}
