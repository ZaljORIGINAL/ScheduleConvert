package database;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Path;
import java.sql.*;

public class DBContext {
    public final String DATABASE_URL;
    public final String DATABASE_NAME;
    public final String TABLE_NAME_PREFIX;
    public final String USER_NAME;
    public final String USER_PASSWORD;

    public DBContext(Path configFile) {
        Config config = ConfigFactory.parseFile(configFile.toFile()).getConfig("db");
        if (config == null)
            throw new RuntimeException("Фаил конфигурации не содержит требуемых параметров: db.*");

        DATABASE_URL = config.getString(DBHelper.CF_DB_URL);
        DATABASE_NAME = config.getString(DBHelper.CF_DB_NAME);
        TABLE_NAME_PREFIX = config.getString(DBHelper.CF_TABLE_PREFIX);
        USER_NAME = config.getString(DBHelper.CF_USER_NAME);
        USER_PASSWORD = config.getString(DBHelper.CF_USER_PASSWORD);
    }

    public Connection open() throws SQLException {
        return DriverManager.getConnection(
                DATABASE_URL +
                        DATABASE_NAME);
    }
}
