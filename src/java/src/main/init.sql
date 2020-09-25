DROP SCHEMA IF EXISTS exam CASCADE;
CREATE SCHEMA exam;
-- Uncomment two lines when it's the first time running the script
-- CREATE TYPE usertype as ENUM('student','admin','instructor');
-- CREATE TYPE questiontype as ENUM('multiplechoice','shortanswer');
CREATE TABLE IF NOT EXISTS exam.users(
	id SERIAL UNIQUE,
	username VARCHAR(30) UNIQUE,
	password VARCHAR(50) NOT NULL,
	show_name VARCHAR(40) NOT NULL,
	users_type USERTYPE,
	PRIMARY KEY (id,username)
);

CREATE TABLE IF NOT EXISTS exam.subject(
	id SERIAL UNIQUE,
	show_name VARCHAR(50) NOT NULL,
	description TEXT,
	instructorId INT NOT NULL,
	FOREIGN KEY (instructorId) REFERENCES exam.users(id),
	PRIMARY KEY (id, instructorId)
);
CREATE TABLE IF NOT EXISTS exam.student_subject_relation(
	id SERIAL UNIQUE,
	studentId INT NOT NULL,
	subjectId INT NOT NULL,
	FOREIGN KEY(studentId) REFERENCES exam.users(id),
	FOREIGN KEY(subjectId) REFERENCES exam.subject(id),
	PRIMARY KEY(id, studentId, subjectId)
);

CREATE TABLE IF NOT EXISTS exam.exam(
	id SERIAL UNIQUE ,
	show_name VARCHAR(50) NOT NULL,
	subjectId INT NOT NULL,
	FOREIGN KEY (subjectId) REFERENCES exam.subject(id),
	isPublished BOOLEAN NOT NULL DEFAULT FALSE,
    isClosed BOOLEAN NOT NULL DEFAULT TRUE,
	isstart BOOLEAN NOT NULL DEFAULT FALSE,
	description TEXT,
	PRIMARY KEY (id, subjectId)
);

CREATE TABLE IF NOt EXISTS exam.question (
	id SERIAL UNIQUE,
	examId INT NOT NULL,
	FOREIGN KEY (examId) REFERENCES exam.exam (id),
	question_type questionType NOT NULL,
	description TEXT NOT NULL,
	mark INT NOT NULL,
    options TEXT,
	PRIMARY KEY(id, examId)
);

CREATE TABLE IF NOT EXISTS exam.submission(
	id SERIAL UNIQUE,
	studentId INT NOT NULL,
	examId INT NOT NULL,
	FOREIGN KEY (studentId) REFERENCES exam.users(id),
	FOREIGN KEY (examId) REFERENCES exam.exam(id),
	mark INT NOT NULL,
	PRIMARY KEY (id, studentId, examId)
);
CREATE TABLE IF NOT EXISTS exam.answer(
	id SERIAL UNIQUE,
	submissionId INT NOT NULL,
	questionId INT NOT NULL,
	FOREIGN KEY (submissionId) REFERENCES exam.submission (id),
	FOREIGN KEY (questionId) REFERENCES exam.question (id),
	mark INT NOT NULL,
	PRIMARY KEY (id,submissionId, questionId)
);
