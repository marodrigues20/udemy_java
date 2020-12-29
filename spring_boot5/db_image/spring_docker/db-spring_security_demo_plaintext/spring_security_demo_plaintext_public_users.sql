create table users
(
    username varchar(50) default NULL::character varying not null
        constraint users_pkey
            primary key,
    password varchar(50) default NULL::character varying,
    enabled  integer
);

alter table users
    owner to springstudent;

INSERT INTO users (username, password, enabled) VALUES ('john', '{noop}test123', 1);
INSERT INTO users (username, password, enabled) VALUES ('mary', '{noop}test123', 1);
INSERT INTO users (username, password, enabled) VALUES ('susan', '{noop}test123', 1);
