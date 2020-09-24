INSERT INTO exam.users (username, password, show_name, users_type)
VALUES ('eduardo', 'eduardo123', 'Eduardo Araujo Oliveira', 'admin'),
       ('maria', 'maria123', 'Maria Rodriguez Read', 'instructor'),
       ('toby', 'toby123', 'Toby Murray', 'instructor'),
       ('ben', 'ben123', 'Ben Stevenson', 'instructor'),
       ('zhazz', 'zhazz123', 'Zihan Zhang', 'student'),
       ('tonys', 'tony123', 'Tony Stark', 'student'),
       ('stever', 'steve123', 'Steve Rogers', 'student');

INSERT INTO exam.users (username, password, show_name, users_type)
VALUES ('markt', 'mark123', 'Mark Timothy', 'student'),
       ('ashj', 'ash123', 'Ash John', 'student');

INSERT INTO exam.subject (show_name, description, instructorid)
VALUES ('SWEN9007', 'Software Design and Architecture', 10),
       ('SWEN90010', 'High Integrity Systems Engineering', 11),
       ('COMP90043', 'Cryptography and Security', 12);

INSERT INTO exam.subject (show_name, description, instructorid)
VALUES ('SWEN30006', 'Software Modelling and Design', 10),
       ('COMP30027', 'Machine Learning', 10);

INSERT INTO exam.student_subject_relation (studentid, subjectid)
VALUES (13, 1),
       (14, 2),
       (15, 3),
       (17, 4),
       (18, 5),
       (13, 2),
       (14, 3);

INSERT INTO exam.exam (show_name, subjectid, ispublished, isstart, description)
VALUES ('2020 Mid Semester Test 1', 1, true, true, 'This is exam description'),
       ('2020 Mid Semester Test 2', 1, true, true, 'This is exam description'),
       ('2020 Final Exam', 1, false, false, 'This is exam description'),
       ('2020 Quiz 1', 2, true, true, 'This is exam description'),
       ('2020 Quiz 2', 2, false, false, 'This is exam description'),
       ('2020 Quiz 3', 2, true, true, 'This is exam description');

-- INSERT INTO exam.question (examid, question_type, description, mark, options)
-- VALUES (1134, "shortanswer", "Question Description1", 2, null);


-- ALTER TYPE questiontype ADD VALUE 'multiplechoice' BEFORE 'multiple_choice';
ALTER TYPE questiontype ADD VALUE 'shortanswer' BEFORE 'short_answer';