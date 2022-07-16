-- 
DROP TABLE IF EXISTS `metric_table`;
-- NOTE: BIGINT,TIMESTAMP, INTEGER, FLOAT are valid type for both SQLite and MySQL
CREATE TABLE `metric_table` ( `id` BIGINT, `hostname` TEXT NOT NULL,  `timestamp` TIMESTAMP, `memory` INTEGER, `cpu` INTEGER, `disk` FLOAT(6), `load_average` INTEGER, PRIMARY KEY(`id`) );


