package database;

public abstract class DBHelper {
    //Ключи для получения параметров из файла конфигурации базы данных
    public static final String CF_DB_URL = "url";
    public static final String CF_DB_NAME = "name";
    public static final String CF_TABLE_PREFIX = "converter_table_prefix";
    public static final String CF_USER_NAME = "user_name";
    public static final String CF_USER_PASSWORD = "user_password";

    //Наименования колонок
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY_OF_WEEK = "day_of_week";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_WEEK_TYPE = "week_type";
    public static final String COLUMN_DISCIPLINE_NAME = "discipline_name";
    public static final String COLUMN_BUILDING = "building";
    public static final String COLUMN_CLASS_ROOM = "class_room";
    public static final String COLUMN_DISCIPLINE_TYPE = "discipline_type";
    public static final String COLUMN_EDUCATOR = "educator";

    //Ключевые значения
    public static final short TOP_WEEK = 0;
    public static final short LOWER_WEEK = 1;

    public static final short LECTURE = 0;
    public static final short LABORATORY = 1;
    public static final short PRACTICE = 2;

    public static final short MONDAY = 0;
    public static final short TUESDAY = 1;
    public static final short WEDNESDAY = 2;
    public static final short THURSDAY = 3;
    public static final short FRIDAY = 4;
    public static final short SATURDAY = 5;
}
