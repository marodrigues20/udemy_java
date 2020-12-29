create table review
(
    id        serial not null
        constraint review_pkey
            primary key,
    comment   varchar(256) default NULL::character varying,
    course_id integer
        constraint review_course_id_fkey
            references course
);

alter table review
    owner to springstudent;
