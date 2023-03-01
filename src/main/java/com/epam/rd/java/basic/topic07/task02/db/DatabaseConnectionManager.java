package com.epam.rd.java.basic.topic07.task02.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.epam.rd.java.basic.topic07.task02.Constants.SETTINGS_FILE;

/**
 * A class that manages database connections using the JDBC API.
 * It loads the database connection settings from the {@code app.properties} file.
 */
public class DatabaseConnectionManager implements AutoCloseable {
    private final String URL;
    private final Properties PROPERTIES;
    private Connection connection;

    /**
     * Constructs a new DatabaseConnectionManager object and loads the database connection settings from the
     * {@code app.properties} file.
     */
    public DatabaseConnectionManager() {
        PROPERTIES = new Properties();
        try( FileInputStream in = new FileInputStream(SETTINGS_FILE) ) {
            PROPERTIES.load(in);

            // Get the connection URL from the properties file
            URL = PROPERTIES.getProperty("connection.url");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens a new database connection using the JDBC API.
     *
     * @return a new database connection.
     * @throws SQLException if a database access error occurs or the URL is null.
     */
    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, PROPERTIES);
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     *
     * @throws SQLException if a database access error occurs.
     */
    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
