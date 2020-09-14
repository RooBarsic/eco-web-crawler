package com.company.api.db;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.Properties;

public class AzureDB implements DataBaseConnection{
    private Connection connection;
    private final Properties properties;

    public AzureDB(final @NotNull Properties properties) {
        this.properties = properties;
    }

    @Override
    public boolean connect() {
        try {
            System.out.println("Connecting to the database");
            connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            System.out.println("Database connection COMPLETED test: " + connection.getCatalog());
            return true;
        } catch (SQLException throwables) {
            System.out.println("Database connection failed ");
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean executeQuery(final @NotNull String query) {
        try {
            return connection.prepareStatement(query).execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public @Nullable ResultSet executeSearchQuery(@NotNull String query) {
        try {
            return connection.prepareCall(query).executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
