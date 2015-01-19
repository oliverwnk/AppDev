drop table if exists entries;
create table entries (
	id integer primary key autoincrement,
	user_id integer,
	categorie integer,
	productName text not null,
	name text not null,
	price float,
	quantity integer,
	contactDetails text,
	latitude float,
	longtitude float,
	Timezone text,
	beginTime text,
	endTime text,
	retry boolean,
	activ boolean
);
drop table if exists users;
create table users (
	id integer primary key autoincrement,
	user_id integer
);
