create table customer
(
    id         serial not null
        constraint customer_pkey
            primary key,
    first_name varchar(45) default NULL::character varying,
    last_name  varchar(45) default NULL::character varying,
    email      varchar(45) default NULL::character varying
);

alter table customer
    owner to springstudent;

INSERT INTO customer (id, first_name, last_name, email) VALUES (1, 'John', 'Doe', 'john@luv2code.com');
INSERT INTO customer (id, first_name, last_name, email) VALUES (2, 'Ajay', 'Rao', 'ajay@luv2code.com');
INSERT INTO customer (id, first_name, last_name, email) VALUES (3, 'Mary', 'Public', 'mary@luv2code.com');
INSERT INTO customer (id, first_name, last_name, email) VALUES (4, 'Maxwell', 'Dixon', 'max@luv2code.com');
