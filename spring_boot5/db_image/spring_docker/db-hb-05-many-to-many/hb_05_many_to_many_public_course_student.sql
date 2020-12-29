create table course_student
(
    course_id  integer not null
        constraint course_student_course_id_fkey
            references course,
    student_id integer not null
        constraint course_student_student_id_fkey
            references student,
    constraint course_student_pkey
        primary key (course_id, student_id)
);

alter table course_student
    owner to springstudent;
