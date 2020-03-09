create table message(
id serial,
left_data varchar(100),
right_data varchar(100),
primary key (id));

create table message_diff(
id serial,
dfoffset integer,
dflength integer,
primary key(id));

create table result_diff(
id serial,
dfresult varchar(20),
message integer,
diff integer,
primary key(id),
foreign key(message) references message(id),
foreign key(diff) references message_diff(id));