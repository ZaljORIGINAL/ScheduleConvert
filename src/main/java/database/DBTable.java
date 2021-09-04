package database;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class DBTable {
    protected final String name;
    protected final DBContext context;

    public DBTable(String tableName, DBContext context) throws SQLException {
        this.name = tableName;
        this.context = context;

        //Создать таблицу если нет
        if (!existTable())
            createTable();
    }

    public String getName() {
        return name;
    }

    public boolean existTable() throws SQLException {
        try (var connection = context.open()) {
            DatabaseMetaData metaData = connection.getMetaData();
            var result = metaData.getTables(
                    null,
                    null,
                    name,
                    null);
            return result.next();
        }
    }

    public abstract boolean createTable() throws SQLException;

    public boolean deleteTable() throws SQLException {
        String sqlCommand = "DROP TABLE " + name;

        try (var connection = context.open()){
            PreparedStatement statement = connection.prepareStatement(sqlCommand);
            statement.executeUpdate();
            return !existTable();
        }
    }
}
