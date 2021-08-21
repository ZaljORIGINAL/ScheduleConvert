package Database;

import java.sql.SQLException;

public class GroupScheduleTable extends DBTable {
    public GroupScheduleTable(String tableName, DBContext context) throws SQLException {
        super(tableName, context);
    }

    @Override
    public boolean createTable() throws SQLException {
        try (var connection = context.open()){
            String sqlCommand = "CREATE TABLE " +
                    name + " " +
                    "(" +
                    DBHelper.COLUMN_ID + " bigserial, " +
                    DBHelper.COLUMN_START_TIME + " time without time zone NOT NULL, " +
                    DBHelper.COLUMN_WEEK_TYPE + " smallint NOT NULL, " +
                    DBHelper.COLUMN_DISCIPLINE_NAME + " text NOT NULL, " +
                    DBHelper.COLUMN_BUILDING + " text NOT NULL, " +
                    DBHelper.COLUMN_CLASS_ROOM + " text NOT NULL, " +
                    DBHelper.COLUMN_DISCIPLINE_TYPE + " smallint NOT NULL, " +
                    DBHelper.COLUMN_EDUCATOR + " text NOT NULL, " +
                    "PRIMARY KEY (id)" +
                    "); \n" +
                    "ALTER TABLE " + name + " OWNER TO " + context.USER_NAME + ";";

            try(var statement =
                        connection.prepareStatement(sqlCommand)){
                return statement.execute();
            }
        }
    }
}
