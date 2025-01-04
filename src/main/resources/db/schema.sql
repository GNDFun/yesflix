CREATE TABLE users(
    user_id integer PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name text NOT NULL,
    password text NOT NULL,
    email text NOT NULL,
    registration_datetime timestamp
);
DROP TABLE users;