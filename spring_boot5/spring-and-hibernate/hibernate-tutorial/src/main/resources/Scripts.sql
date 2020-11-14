-- Create user postgres
CREATE USER hbstudent SUPERUSER;

-- createdb hb_student_tracker
createdb hb_student_tracker

-- List databases
psql -U postgres -l

-- Show tables in database
psql -U postgres -d hb_student_tracker

-- Drop database
dropdb hb_student_tracker


-- To change user password:
ALTER USER hbstudent WITH PASSWORD 'hbstudent';

-- connect into hb_student_tracker
\c hb_student_tracker


-- Create a table
CREATE TABLE student(
   id SERIAL NOT NULL,
   first_name VARCHAR(45) NOT NULL,
   last_name VARCHAR(45) NOT NULL,
   email VARCHAR(45) NOT NULL,
   PRIMARY KEY (id)
);
