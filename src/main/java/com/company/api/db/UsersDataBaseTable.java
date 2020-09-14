package com.company.api.db;

import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDataBaseTable {
    private final String TABLE_NAME = "users";
    private final DataBaseConnection dataBaseConnection;

    public UsersDataBaseTable(final @NotNull DataBaseConnection connection) {
        dataBaseConnection = connection;
    }

    public boolean searchUserByLogin(final @NotNull String login) {
        ResultSet executionResult = dataBaseConnection.executeSearchQuery(
                "select * from " + TABLE_NAME + " where "
                        + " login = '" + login + "'"
                        + ";");
        try {
            return executionResult != null ? executionResult.next() : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean searchUser(final @NotNull UserDAO userDAO) {
        ResultSet executionResult = dataBaseConnection.executeSearchQuery(
                "select * from " + TABLE_NAME + " where "
                        + " login = " + userDAO.getLoginDAO()
                        + " and "
                        + " firstName = " + userDAO.getFirstNameDAO()
                        + " and "
                        + " lastName = " + userDAO.getLastNameDAO()
                        + ";");
        try {
            return executionResult != null ? executionResult.next() : false;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean setUserFromDB(final @NotNull UserDAO userDAO) {
        ResultSet executionResult = dataBaseConnection.executeSearchQuery(
                "select * from " + TABLE_NAME + " where "
                        + " login = " + userDAO.getLoginDAO()
                        + " and "
                        + " firstName = " + userDAO.getFirstNameDAO()
                        + " and "
                        + " lastName = " + userDAO.getLastNameDAO()
                        + ";");
        try {
            if (executionResult != null) {
                executionResult.next();
                userDAO.setIncrementRequestsNumber(executionResult.getInt("requestsNumber"));
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean insertUser(final @NotNull UserDAO user) {
        //insert into users (login, firstName, LastName) values ('test_login', 'test_firstName', 'test_lastName');
        if (searchUser(user))
            return false;
        return dataBaseConnection.executeQuery(
            "INSERT INTO " + TABLE_NAME + " (login, firstName, lastName, requestsNumber) "
                    + "VALUES ("
                    + user.getLoginDAO()
                    + ", "
                    + user.getFirstNameDAO()
                    + ", "
                    + user.getLastNameDAO()
                    + ", "
                    + user.getRequestsNumberDAO()
                    + " );");
    }

    public boolean updateUserRequestNumber(final @NotNull UserDAO userDAO) {
        //update users SET requestsNumber = 15 WHERE login = 'test_login' and firstName = 'test_firstName' and LastName = 'test_lastName';
        return dataBaseConnection.executeQuery(
            "UPDATE " + TABLE_NAME
                    + " SET requestsNumber = " + userDAO.getRequestsNumberDAO()
                    + " WHERE "
                    + " login = " + userDAO.getLoginDAO()
                    + " and "
                    + " firstName = " + userDAO.getFirstNameDAO()
                    + " and "
                    + " lastName = " + userDAO.getLastNameDAO()
                    + ";");
    }



}
