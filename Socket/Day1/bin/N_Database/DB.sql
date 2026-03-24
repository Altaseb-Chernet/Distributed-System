CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10),
    department VARCHAR(50)
);

INSERT INTO students (name, age, gender, department) VALUES
('Abel', 22, 'Male', 'Software Engineering'),
('Sara', 20, 'Female', 'Information System'),
('Helen', 23, 'Female', 'Computer Science'),
('John', 24, 'Male', 'Software Engineering'),
('Liya', 21, 'Female', 'Information System');