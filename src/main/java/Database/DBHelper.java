package Database;

public abstract class DBHelper {
    //Ключи для получения параметров из файла конфигурации базы данных
    public static final String CF_DB_URL = "url";
    public static final String CF_DB_NAME = "name";
    public static final String CF_TABLE_PREFIX = "converter_table_prefix";
    public static final String CF_USER_NAME = "user_name";
    public static final String CF_USER_PASSWORD = "user_password";

    //Наименования колонок
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_WEEK_TYPE = "week_type";
    public static final String COLUMN_DISCIPLINE_NAME = "discipline_name";
    public static final String COLUMN_BUILDING = "building";
    public static final String COLUMN_CLASS_ROOM = "class_room";
    public static final String COLUMN_DISCIPLINE_TYPE = "discipline_type";
    public static final String COLUMN_EDUCATOR = "educator";

    //Ключевые значения
    public static final String TOP_WEEK_STR = "в";
    public static final byte TOP_WEEK = 0;
    public static final String LOWER_WEEK_STR = "н";
    public static final byte LOWER_WEEK = 0;

    public static final String LECTURE_STR = "лек";
    public static final byte LECTURE = 0;
    public static final String LABORATORY_STR = "лаб";
    public static final byte LABORATORY = 1;
    public static final String PRACTICE_STR = "пр";
    public static final byte PRACTICE = 2;
}
