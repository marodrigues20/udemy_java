-- createdb hb-03-one-to-many
createdb "hb-03-one-to-many";

DROP TABLE IF EXISTS "hb-03-one-to-many";

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
  tittle VARCHAR(128) UNIQUE,
  instructor_id INTEGER DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (instructor_id) REFERENCES instructor (id)
);
