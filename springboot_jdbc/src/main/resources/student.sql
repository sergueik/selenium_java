exec sys.sp_readerrorlog 0, 1, 'listening';
create database student;
use student;

create table student(
id      bigint primary key identity(1,1),
name    NVARCHAR(30) not null,
course	NVARCHAR(30) not null,
addtime datetime not null default current_timestamp);

insert into student(name,course)values('Jack','Chinese');
insert into student(name,course)values('Tom','Computer');