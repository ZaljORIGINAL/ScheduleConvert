package excelReader;

public abstract class ExcelHelper {
    //Относительный адрес колонок
    public final static int INDEX_DAY_OF_WEEK = 0;
    public final static int INDEX_TIME = 1;
    public final static int INDEX_WEEK_TYPE = 2;
    public final static int INDEX_DISCIPLINE_NAME = 3;
    public final static int INDEX_BUILDING = 4;
    public final static int INDEX_CLASS_ROOM = 5;
    public final static int INDEX_DISCIPLINE_TYPE = 6;
    public final static int INDEX_EDUCATOR = 9;

    public static final String TOP_WEEK = "в";
    public static final String LOWER_WEEK = "н";

    public static final String LECTURE = "лек";
    public static final String LABORATORY = "лаб";
    public static final String PRACTICE = "пр";

    public static final String MONDAY = "Понедельник";
    public static final String TUESDAY = "Вторник";
    public static final String WEDNESDAY = "Среда";
    public static final String THURSDAY = "Четверг";
    public static final String FRIDAY = "Пятница";
    public static final String SATURDAY = "Суббота";
}
