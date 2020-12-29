create table instructor_detail
(
    id              serial not null
        constraint instructor_detail_pkey
            primary key,
    youtube_channel varchar(128) default NULL::character varying,
    hobby           varchar(45)  default NULL::character varying
);

alter table instructor_detail
    owner to springstudent;

INSERT INTO instructor_detail (id, youtube_channel, hobby) VALUES (1, 'http://www.youtube.com', 'Video Games');
