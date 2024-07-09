CREATE SCHEMA axion_auth_db;

CREATE TABLE axion_auth_db.users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE axion_auth_db.passwords (
    user_id INT PRIMARY KEY,
    password_hash CHAR(60) NOT NULL
);
