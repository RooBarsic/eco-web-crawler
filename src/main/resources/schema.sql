DROP TABLE IF EXISTS users;
CREATE TABLE users (
       id INT IDENTITY(1,1) PRIMARY KEY,
       login VARCHAR(255),
       firstName VARCHAR(1700),
       LastName VARCHAR(1700),
       requestsNumber Int DEFAULT 0
       UNIQUE (login, firstName, LastName)
);
