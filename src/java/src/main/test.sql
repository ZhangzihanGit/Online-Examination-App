INSERT INTO exam.users (username, password, show_name, users_type)
VALUES ('eduardo', 'eduardo123', 'Eduardo Araujo Oliveira', 'admin'),
       ('maria', 'maria123', 'Maria Rodriguez Read', 'instructor'),
       ('toby', 'toby123', 'Toby Murray', 'instructor'),
       ('ben', 'ben123', 'Ben Stevenson', 'instructor'),
       ('zhazz', 'zhazz123', 'Zihan Zhang', 'student'),
       ('tonys', 'tony123', 'Tony Stark', 'student'),
       ('stever', 'steve123', 'Steve Rogers', 'student');

INSERT INTO exam.subject (show_name, description, instructorid)
VALUES ('SWEN9007', 'Software Design and Architecture', 10),
       ('SWEN90010', 'High Integrity Systems Engineering', 11),
       ('COMP90043', 'Cryptography and Security', 12);

INSERT INTO exam.subject (show_name, description, instructorid)
VALUES ('SWEN30006', 'Software Modelling and Design', 10),
       ('COMP30027', 'Machine Learning', 10);