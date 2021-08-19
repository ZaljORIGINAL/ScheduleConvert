package excelReader;

public class GroupAddress {
    private final int indexX;
    private final int indexY;
    private final int pageIndex;

    public GroupAddress(int indexX, int indexY, int pageIndex) {
        this.indexX = indexX;
        this.indexY = indexY;
        this.pageIndex = pageIndex;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public int getPageIndex() {
        return pageIndex;
    }
}
