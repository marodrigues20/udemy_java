create table course
(
    id            serial not null
        constraint course_pkey
            primary key,
    title         varchar(128)
        constraint course_title_key
            unique,
    instructor_id integer
        constraint course_instructor_id_fkey
            references instructor
);

alter table course
    owner to springstudent;
