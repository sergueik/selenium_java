-- https://www.sqlite.org/datatype3.html
drop table if exists "metric_table";
-- NOTE: BIGINT is valid type for both SQLite and MySQL

CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TEXT, `memory` TEXT, `cpu` TEXT, `disk` TEXT, `load_average` TEXT, PRIMARY KEY(`id`) );

