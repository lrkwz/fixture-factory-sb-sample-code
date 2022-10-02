create table course_student_subscription(
id_student varchar(100) not null,
id_course varchar(100) not null,
status varchar(100) not null,
constraint pk_course_student_subs primary key (id_student,id_course),
constraint fk_course_st_subs_course foreign key (id_course) references course(id),
constraint fk_course_st_subs_student foreign key (id_student) references student(id),
);