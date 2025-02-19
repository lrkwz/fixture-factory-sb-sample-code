CREATE table course(
id varchar(100) not null,
init_date date not null,
end_date date not null,
description varchar(500),
constraint pk_course primary key(id)
);

