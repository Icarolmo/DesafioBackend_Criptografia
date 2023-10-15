CREATE TABLE user_transfer (
    id SERIAL PRIMARY KEY UNIQUE NOT NULL,
    userDocument VARCHAR(70) NOT NULL,
    creditCardToken VARCHAR(70) NOT NULL,
    transferValue DECIMAL NOT NULL
);