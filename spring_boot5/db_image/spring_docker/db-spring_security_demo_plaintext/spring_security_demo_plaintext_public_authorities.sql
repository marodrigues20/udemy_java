create table authorities
(
    username  varchar(50) default NULL::character varying not null
        constraint authorities_username_fkey
            references users,
    authority varchar(50) default NULL::varchar not null,
    constraint authorities_pkey
        primary key (username, authority)
);

alter table authorities
    owner to springstudent;

INSERT INTO authorities (username, authority) VALUES ('john', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('mary', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('mary', 'ROLE_MANAGER');
INSERT INTO authorities (username, authority) VALUES ('susan', 'ROLE_EMPLOYEE');
INSERT INTO authorities (username, authority) VALUES ('susan', 'ROLE_ADMIN');
