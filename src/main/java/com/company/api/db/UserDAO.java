package com.company.api.db;

import org.jetbrains.annotations.NotNull;

public class UserDAO {
    @NotNull
    private final String login;
    @NotNull
    private final String firstName;
    @NotNull
    private final String lastName;
    private int requestsNumber;

    public UserDAO(final @NotNull String login, final @NotNull String firstName, final @NotNull String lastName) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        requestsNumber = 0;
    }

    public UserDAO(final @NotNull String login, final @NotNull String firstName, final @NotNull String lastName, int requestsNumber) {
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.requestsNumber = requestsNumber;
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getRequestsNumber() { return requestsNumber; }

    public String getLoginDAO() {
        return "'" + getLogin() + "'";
    }

    public String getFirstNameDAO() {
        return "'" + getFirstName() + "'";
    }

    public String getLastNameDAO() {
        return "'" + getLastName() + "'";
    }

    public int getRequestsNumberDAO() {
        return getRequestsNumber();
    }

    public void incrementRequestsNumber() {
        requestsNumber++;
    }

    public void setIncrementRequestsNumber(int requestsNumber) {
        this.requestsNumber = requestsNumber;
    }
}
