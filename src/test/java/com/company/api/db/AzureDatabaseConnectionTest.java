package com.company.api.db;

import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertTrue;

public class AzureDatabaseConnectionTest {
    private final String connectionUrl;
    private final String connectionUserName;
    private final String connectionPassword;
    private final Properties connectionProperties;

    public AzureDatabaseConnectionTest() {
        connectionUrl = System.getenv("url");
        connectionUserName = System.getenv("user");
        connectionPassword = System.getenv("password");

        System.out.println("log -- connectionUrl = " + connectionUrl);
        System.out.println("log -- connectionUserName = " + connectionUserName);
        System.out.println("log -- connectionPassword = " + connectionPassword);

        connectionProperties = new Properties();
        connectionProperties.setProperty("url", connectionUrl);
        connectionProperties.setProperty("user", connectionUserName);
        connectionProperties.setProperty("password", connectionPassword);
    }

    @Test
    public void testDatabaseConnection() {
        AzureDB azureDB = new AzureDB(connectionProperties);
        assertTrue(azureDB.connect());
    }
}
