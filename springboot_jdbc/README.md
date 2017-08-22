### Info

This directory contains a skeleton spring/sqlite JDBC project based on
[Spring Boot MVC Demo with JDBC and SqlServer](https://github.com/wuwei1024/SpringBoot-MVC-JDBC-SqlServer)

It opeates via static methods `JDBCUtils.getConnection()`, `JDBCUtils.TranverseToList()` and plain SQL - not using JPA.

To build, create the sqlite database directory
```sh
pushd ~
mkdir sqlite
```
and create database `~/sqlite/springboot.db` with table
```sql
CREATE TABLE `student` (
	`id`	INTEGER,
	`name`	TEXT NOT NULL,
	`course`	TEXT NOT NULL,
	PRIMARY KEY(`id`)
);
```
and run application
```cmd
mvn clean spring-boot:run
```
For SQLite Hibernate project example, see [Spring-Boot-SQLite](https://github.com/restart1025/Spring-Boot-SQLite)
