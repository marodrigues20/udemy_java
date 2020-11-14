-- createdb hb_student_tracker
createdb "spring_security_demo_bcrypt";

DROP TABLE IF EXISTS spring_security_demo_bcrypt;

CREATE TABLE users (
  username VARCHAR(50) DEFAULT NULL,
  password VARCHAR(68) DEFAULT NULL,
  enabled INTEGER DEFAULT NULL,
  PRIMARY KEY (username)
);

--
-- Inserting data for table `users`
--

INSERT INTO users
VALUES
('john','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1),
('mary','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1),
('susan','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1);


--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS authorities;

CREATE TABLE authorities (
  username VARCHAR(50) DEFAULT NULL,
  authority VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (username, authority),
  FOREIGN KEY (username) REFERENCES users (username));

  --
  -- Inserting data for table `authorities`
  --

  INSERT INTO authorities
  VALUES
  ('john','ROLE_EMPLOYEE'),
  ('mary','ROLE_EMPLOYEE'),
  ('mary','ROLE_MANAGER'),
  ('susan','ROLE_EMPLOYEE'),
  ('susan','ROLE_ADMIN');
