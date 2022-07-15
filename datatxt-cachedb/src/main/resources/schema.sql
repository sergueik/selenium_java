-- https://www.sqlite.org/datatype3.html
DROP TABLE IF EXISTS `metric_table`;
-- NOTE: BIGINT is valid type for both SQLite and MySQL

CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TEXT, `memory` TEXT, `cpu` TEXT, `disk` TEXT, `load_average` TEXT, PRIMARY KEY(`id`) );
-- for MySQL
CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TIMESTAMP, `memory` INTEGER, `cpu` INTEGER, `disk` FLOAT(6), `load_average` INTEGER, PRIMARY KEY(`id`) );


