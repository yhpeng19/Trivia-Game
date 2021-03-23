CREATE DATABASE restapi;

USE restapi;

CREATE TABLE question (
	question VARCHAR(500) AUTO_INCREMENT PRIMARY KEY,
    correct_answer VARCHAR(500) NOT NULL,
    wrong_answer1 VARCHAR(500) NOT NULL,
    wrong_answer2 VARCHAR(500) NOT NULL,
    wrong_answer3 VARCHAR(500) NOT NULL,
    category VARCHAR(500) NOT NULL
);