-- schema to build "host_inventory"  which will not be available on the same JDBC connection as metrics data
-- SQLite
DROP TABLE IF EXISTS `host_inventory`;
CREATE TABLE `host_inventory` ( `id` BIGINT, `server` TEXT NOT NULL, `instance` TEXT, `application` TEXT, PRIMARY KEY(`id`) )

