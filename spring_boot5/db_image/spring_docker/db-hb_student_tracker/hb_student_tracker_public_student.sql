create table student
(
    id         serial      not null
        constraint student_pkey
            primary key,
    first_name varchar(45) not null,
    last_name  varchar(45) not null,
    email      varchar(45) not null
);

alter table student
    owner to springstudent;

INSERT INTO student (id, first_name, last_name, email) VALUES (1, 'Paul', 'Wall', 'foo@gmail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (2, 'Bonita', 'Applebaum', 'foo@gmail.com');
INSERT INTO student (id, first_name, last_name, email) VALUES (3, 'Daffy', 'Duck', 'foo@gmail.com');
