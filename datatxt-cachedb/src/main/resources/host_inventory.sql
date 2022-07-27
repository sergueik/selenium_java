-- schema to build "host_inventory"  which will not be available on the same JDBC connection as metrics data
-- SQLite
DROP TABLE IF EXISTS `host_inventory`;
CREATE TABLE `host_inventory` ( `id` INTEGER, `hostname` TEXT NOT NULL, `appid` TEXT, `environment` TEXT, `datacenter` TEXT, `addtime` TEXT, PRIMARY KEY(`id`) )

