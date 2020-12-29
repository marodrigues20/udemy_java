create table student
(
    id         serial not null
        constraint student_pkey
            primary key,
    first_name varchar(45) default NULL::character varying,
    last_name  varchar(45) default NULL::character varying,
    email      varchar(45) default NULL::character varying
);

alter table student
    owner to springstudent;

INSERT INTO student (id, first_name, last_name, email) VALUES (1, 'John', 'Doe', 'john@luv2code.com');
