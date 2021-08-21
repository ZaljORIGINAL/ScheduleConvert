package Database;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablesManager {
    private final DBContext context;
    private final List<String> tables;

    public TablesManager(Path config) {
        context = new DBContext(config);
        tables = new ArrayList<>();

        try (Connection connection = open()) {
            String sqlCommand = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        tables.add(result.getString("table_name"));
                    }
                }
            };
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Connection open() throws SQLException {
        return DriverManager.getConnection(
                context.DATABASE_URL +
                        context.DATABASE_NAME);
    }

    public List<String> getTables() {
        return tables;
    }
}
