CREATE TABLE IF NOT EXISTS client (
    id IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    genre VARCHAR(10) NOT NULL,
    address VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    age INT NOT NULL,
    client_id VARCHAR(10) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    status BIT NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    id IDENTITY NOT NULL PRIMARY KEY,
    number BIGINT NOT NULL,
    type VARCHAR(10) NOT NULL,
    initial_balance DOUBLE NOT NULL DEFAULT 0,
    status BIT NOT NULL DEFAULT 1,
    client_id BIGINT NOT NULL
);

ALTER TABLE account ADD FOREIGN KEY (client_id) REFERENCES client(id);

CREATE TABLE IF NOT EXISTS movement (
    id IDENTITY PRIMARY KEY,
    submission_date DATE NOT NULL,
    amount DOUBLE NOT NULL,
    balance DOUBLE NOT NULL,
    account_id BIGINT NOT NULL
);

ALTER TABLE movement ADD FOREIGN KEY (account_id) REFERENCES account(id);