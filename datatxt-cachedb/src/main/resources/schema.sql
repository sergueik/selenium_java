-- 
DROP TABLE IF EXISTS `metric_table`;
-- NOTE: BIGINT,TIMESTAMP, FLOAT(6), FLOAT are valid type for both SQLite and MySQL
CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TIMESTAMP, `memory` FLOAT(6), `cpu` FLOAT(6), `disk` FLOAT(6), `load_average` FLOAT(6), PRIMARY KEY(`id`) );


