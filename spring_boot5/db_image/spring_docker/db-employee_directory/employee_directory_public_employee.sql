create table springcourse.employee
(
    id         serial not null
        constraint employee_pkey
            primary key,
    first_name varchar(45) default NULL::character varying,
    last_name  varchar(45) default NULL::character varying,
    email      varchar(45) default NULL::character varying
);

alter table springcourse.employee
    owner to springstudent;

INSERT INTO employee (id, first_name, last_name, email) VALUES (1, 'Leslie', 'Andrews', 'leslie@luv2code.com');
INSERT INTO employee (id, first_name, last_name, email) VALUES (2, 'Emma', 'Baumgarten', 'emma@luv2code.com');
INSERT INTO employee (id, first_name, last_name, email) VALUES (3, 'Avani', 'Gupta', 'avani@luv2code.com');
INSERT INTO employee (id, first_name, last_name, email) VALUES (4, 'Yuri', 'Petrov', 'yuri@luv2code.com');
INSERT INTO employee (id, first_name, last_name, email) VALUES (5, 'Juan', 'Vega', 'juan@luv2code.com');
