create table member(
	id varchar(50) primary key,
	pwd varchar(50) not null,
	name varchar(50) not null,
	email varchar(50) unique,
	auth int
);

create table bbs(
	seq int auto_increment primary key,
	id varchar(50) not null,
	
	ref decimal(8) not null,
	step decimal(8) not null,
	depth decimal(8) not null,
	
	title varchar(200) not null,
	content varchar(4000) not null,
	wdate timestamp not null,
	
	del decimal(1) not null,
	readcount decimal(8) not null	
);

alter table bbs
add foreign key(id) references member(id);

insert into bbs(id, ref, step, depth, title, content, wdate, del, readcount)
values('id', (select ifnull(max(ref), 0)+1 from bbs b), 0, 0, 'title', 'content', now(), 0, 0));


-- 달력
create table calendar(
	seq int auto_increment primary key, -- decimal
	id varchar(50) not null,
	title varchar(200) not null,
	content varchar(4000),
	rdate varchar(12) not null, -- 202307041430
	wdate timestamp not null
	);
	
	alter table calendar
	add
	constraint fk_cal_id foreign key(id)
	references member(id);
	
	-- 댓글
	create table bbscomment(
	comment_id int auto_increment primary key,
	seq int not null,
	id varchar(50) not null,
	content varchar(1000) not null,
	wdate timestamp not null
);

	