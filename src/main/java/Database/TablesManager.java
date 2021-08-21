package Database;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TablesManager {
    private final DBContext context;
    private final List<String> tablesNames;

    public TablesManager(Path config) {
        context = new DBContext(config);
        tablesNames = new ArrayList<>();

        try (Connection connection = context.open()) {
            String sqlCommand = "SELECT table_name FROM information_schema.tables WHERE table_schema='public'";
            try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        tablesNames.add(result.getString("table_name"));
                    }
                }
            };
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public List<String> getTablesNames() {
        return tablesNames;
    }

    public GroupScheduleTable getTable(int groupNumber) throws SQLException {
        String expectedName = context.TABLE_NAME_PREFIX + "_" + groupNumber;
        return new GroupScheduleTable(expectedName, context);
    }
}
