-- createdb hb_student_tracker
createdb "spring_security_demo_plaintext";

DROP TABLE IF EXISTS spring_security_demo_plaintext;

CREATE TABLE users (
  username VARCHAR(50) DEFAULT NULL,
  password VARCHAR(50) DEFAULT NULL,
  enabled INTEGER DEFAULT NULL,
  PRIMARY KEY (username)
);

--
-- Inserting data for table `users`
--

INSERT INTO users
VALUES
('john','{noop}test123',1),
('mary','{noop}test123',1),
('susan','{noop}test123',1);


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
