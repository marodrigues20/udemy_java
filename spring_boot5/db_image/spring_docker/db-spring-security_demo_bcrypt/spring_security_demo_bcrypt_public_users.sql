create table users
(
    username varchar(50) default NULL::character varying not null
        constraint users_pkey
            primary key,
    password varchar(68) default NULL::character varying,
    enabled  integer
);

alter table users
    owner to springstudent;

INSERT INTO users (username, password, enabled) VALUES ('john', '{bcrypt}$2b$10$wdScagE2U/934qBCzRBOg.KVZrOAFGIPQQD/9zVOhIuA3/S8/NBrO', 1);
INSERT INTO users (username, password, enabled) VALUES ('mary', '{bcrypt}$2b$10$wdScagE2U/934qBCzRBOg.KVZrOAFGIPQQD/9zVOhIuA3/S8/NBrO', 1);
INSERT INTO users (username, password, enabled) VALUES ('susan', '{bcrypt}$2b$10$wdScagE2U/934qBCzRBOg.KVZrOAFGIPQQD/9zVOhIuA3/S8/NBrO', 1);
