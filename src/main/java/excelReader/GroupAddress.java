package excelReader;

public class GroupAddress {
    private final int rowIndex;
    private final int columnIndex;
    private final int pageIndex;

    public GroupAddress(int rowIndex, int columnIndex, int pageIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.pageIndex = pageIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getPageIndex() {
        return pageIndex;
    }
}
