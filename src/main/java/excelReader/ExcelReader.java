package excelReader;

import Database.DBHelper;
import Database.GroupScheduleTable;
import Database.Row;
import Schedule.TimeSchedule;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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


    public void extractGroupSchedule(int number, TimeSchedule timeSchedule, GroupScheduleTable table) throws SQLException {
        GroupAddress address = groupAddressMap.get(number);
        HSSFRow row;
        short dayOfWeek = -1;
        short time;
        String disciplineName;
        for (int index = 0; true; index++) {
            row = book.getSheetAt(address.getPageIndex()).getRow(address.getRowIndex() + index);
            if (row == null)
                break;
            dayOfWeek = getDayOfWeek(address, row, dayOfWeek);

            time = getTime(address, row, timeSchedule);
            if (time == -1)
                break;

            disciplineName = getDisciplineName(address, row);
            if (disciplineName.isEmpty())
                continue;

            try {
                table.insert(new Row(
                        dayOfWeek,
                        time,
                        getWeekType(address, row),
                        disciplineName,
                        getBuilding(address, row),
                        getClassRoom(address, row),
                        getDisciplineType(address, row),
                        getEducator(address, row)));
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
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
                if (cell == null)
                    break;
                String value = cell.getRichStringCellValue().toString();
                if (value.isEmpty())
                    break;

                int groupNumber = Integer.parseInt(value.substring(0, value.indexOf(" ")));
                GroupAddress address = new GroupAddress(groupNumber,2, columnIndex - 3, sheetIndex);
                groupAddressMap.put(
                        address.getNumber(),
                        address);
            }
        }
    }

    private short getDayOfWeek(GroupAddress address, HSSFRow row, short lastVar) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_DAY_OF_WEEK);
        String value = cell.getStringCellValue();
        if (value.isEmpty())
            return lastVar;
        else {
            lastVar = switch (value) {
                case ExcelHelper.MONDAY -> DBHelper.MONDAY;
                case ExcelHelper.TUESDAY -> DBHelper.TUESDAY;
                case ExcelHelper.WEDNESDAY -> DBHelper.WEDNESDAY;
                case ExcelHelper.THURSDAY -> DBHelper.THURSDAY;
                case ExcelHelper.FRIDAY -> DBHelper.FRIDAY;
                case ExcelHelper.SATURDAY -> DBHelper.SATURDAY;
                default -> throw new RuntimeException("Неверный день недели!");
            };
            return lastVar;
        }
    }

    private short getTime(GroupAddress address, HSSFRow row, TimeSchedule timeSchedule) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_TIME);
        String value = switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> new SimpleDateFormat("HH:mm").format(cell.getDateCellValue());
            default -> null;
        };

        return (short) timeSchedule.getPositionByTime(value);
    }

    private short getWeekType(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_WEEK_TYPE);
        String value = cell.getStringCellValue();
        return switch (value) {
            case ExcelHelper.TOP_WEEK -> DBHelper.TOP_WEEK;
            case ExcelHelper.LOWER_WEEK -> DBHelper.LOWER_WEEK;
            default -> throw new RuntimeException("Неверный тип недели");
        };
    }

    private String getDisciplineName(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_DISCIPLINE_NAME);
        return cell.getStringCellValue();
    }

    private String getBuilding(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_BUILDING);
        return cell.getStringCellValue();
    }

    private String getClassRoom(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_CLASS_ROOM);
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "---";
        };
    }

    private short getDisciplineType(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_DISCIPLINE_TYPE);
        String value = cell.getStringCellValue();
        return switch (value) {
            case ExcelHelper.LECTURE -> DBHelper.LECTURE;
            case ExcelHelper.LABORATORY -> DBHelper.LABORATORY;
            case ExcelHelper.PRACTICE -> DBHelper.PRACTICE;
            default -> (short) -1;
        };
    }

    private String getEducator(GroupAddress address, HSSFRow row) {
        HSSFCell cell = row.getCell(address.getColumnIndex() + ExcelHelper.INDEX_EDUCATOR);
        return cell.getStringCellValue();
    }
}
