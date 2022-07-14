-- https://www.sqlite.org/datatype3.html
drop table if exists "metric_table";
CREATE TABLE "metric_table" ( `id` UNSIGNED BIG INT, `hostname` TEXT NOT NULL,  `timestamp` TEXT, `memory` TEXT, `cpu` TEX, `disk` TEXT, `load_average` TEXT, PRIMARY KEY(`id`) );