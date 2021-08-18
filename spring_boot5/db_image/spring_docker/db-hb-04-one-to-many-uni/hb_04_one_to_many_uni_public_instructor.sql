springstudentcreate table instructor
(
    id                   serial not null
        constraint instructor_pkey
            primary key,
    first_name           varchar(45) default NULL::character varying,
    last_name            varchar(45) default NULL::character varying,
    email                varchar(45) default NULL::character varying,
    instructor_detail_id integer
        constraint instructor_instructor_detail_id_fkey
            references instructor_detail
);

alter table instructor
    owner to springstudent;