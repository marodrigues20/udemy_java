-- createdb hb-04-one-to-many-uni
createdb "hb-04-one-to-many-uni";

DROP TABLE IF EXISTS "hb-04-one-to-many-uni";

DROP TABLE IF EXISTS instructor_detail;

CREATE TABLE instructor_detail (
  id SERIAL NOT NULL,
  youtube_channel VARCHAR(128) DEFAULT NULL,
  hobby VARCHAR(45) DEFAULT NULL,
  PRIMARY KEY (id)
);

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
