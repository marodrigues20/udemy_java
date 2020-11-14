-- createdb hb-05-many-to-many
createdb "hb-05-many-to-many";

DROP TABLE IF EXISTS "hb-05-many-to-many";

DROP TABLE IF EXISTS instructor_detail;

CREATE TABLE instructor_detail (
  id SERIAL NOT NULL,
  youtube_channel VARCHAR(128) DEFAULT NULL,
  hobby VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS instructor;

CREATE TABLE instructor (
  id SERIAL NOT NULL,
  first_name VARCHAR(45) DEFAULT NULL,
  last_name VARCHAR(45) DEFAULT NULL,
  email VARCHAR(45) DEFAULT NULL,
  instructor_detail_id INTEGER DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (instructor_detail_id) REFERENCES instructor_detail (id)
);


DROP TABLE IF EXISTS course;

CREATE TABLE course (
  id SERIAL NOT NULL,
  title VARCHAR(128) UNIQUE,
  instructor_id INTEGER DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);


DROP TABLE IF EXISTS review;

CREATE TABLE review (
  id SERIAL NOT NULL,
  comment VARCHAR(256) DEFAULT NULL,
  course_id INTEGER DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (course_id) REFERENCES course (id)
);


DROP TABLE IF EXISTS student;

CREATE TABLE student (
  id SERIAL NOT NULL,
  first_name VARCHAR(45) DEFAULT NULL,
  last_name VARCHAR(45) DEFAULT NULL,
  email VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (id)
);


DROP TABLE IF EXISTS course_student;

CREATE TABLE course_student (
  course_id INTEGER NOT NULL,
  student_id INTEGER NOT NULL,
  PRIMARY KEY (course_id,student_id),
  FOREIGN KEY (course_id) REFERENCES course (id),
  FOREIGN KEY (student_id) REFERENCES student (id)
);
