package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                    DBHelper.COLUMN_DAY_OF_WEEK + " smallint NOT NULL, " +
                    DBHelper.COLUMN_TIME + " smallint NOT NULL, " +
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

    public Row insert(Row row) throws SQLException {
        try (Connection connection = context.open()) {
            String sqlCommand = "INSERT INTO " +
                    name + " " +
                    "(" +
                    DBHelper.COLUMN_DAY_OF_WEEK + ", " +
                    DBHelper.COLUMN_TIME + ", " +
                    DBHelper.COLUMN_WEEK_TYPE + ", " +
                    DBHelper.COLUMN_DISCIPLINE_NAME + ", " +
                    DBHelper.COLUMN_BUILDING + ", " +
                    DBHelper.COLUMN_CLASS_ROOM + ", " +
                    DBHelper.COLUMN_DISCIPLINE_TYPE + ", " +
                    DBHelper.COLUMN_EDUCATOR +
                    ") " +
                    "VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?, ?) " +
                    "RETURNING " + DBHelper.COLUMN_ID + ";";

            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                statement.setShort(1, row.getDayOfWeek());
                statement.setShort(2, row.getTime());
                statement.setShort(3, row.getWeekType());
                statement.setString(4, row.getDisciplineName());
                statement.setString(5, row.getBuilding());
                statement.setString(6, row.getClassRoom());
                statement.setShort(7, row.getDisciplineType());
                statement.setString(8, row.getEducator());

                ResultSet result = statement.executeQuery();
                return new Row(result.getInt(DBHelper.COLUMN_ID), row);
            }
        }
    }

    public List<Row> getRowsDay(short dayOfWeek, short weekType) throws SQLException {
        try (Connection connection = context.open()) {
            String sqlCommand = "SELECT * FROM " +
                    name + " " +
                    "WHERE " +
                    DBHelper.COLUMN_DAY_OF_WEEK + "=? " +
                    "AND " +
                    DBHelper.COLUMN_WEEK_TYPE + "=?;";

            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                statement.setShort(1, dayOfWeek);
                statement.setShort(2, weekType);
                ResultSet result = statement.executeQuery();
                return extractRows(result);
            }
        }
    }

    private List<Row> extractRows(ResultSet result) throws SQLException {
        List<Row> rows = new ArrayList<>();
        while (result.next()) {
            rows.add(new Row(
                    result.getInt(DBHelper.COLUMN_ID),
                    result.getShort(DBHelper.COLUMN_DAY_OF_WEEK),
                    result.getShort(DBHelper.COLUMN_TIME),
                    result.getShort(DBHelper.COLUMN_WEEK_TYPE),
                    result.getString(DBHelper.COLUMN_DISCIPLINE_NAME),
                    result.getString(DBHelper.COLUMN_BUILDING),
                    result.getString(DBHelper.COLUMN_CLASS_ROOM),
                    result.getShort(DBHelper.COLUMN_DISCIPLINE_TYPE),
                    result.getString(DBHelper.COLUMN_EDUCATOR)
            ));
        }

        return rows;
    }
}
