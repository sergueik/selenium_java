-- for 5 minute average downsampling one formula can be:
-- substract FROM x.timestamp its minute remainder over 5 GROUP BY that column and all inventory columns like instance:

SELECT AVG(value),instance, STRFTIME('%Y-%m-%d ' || '%H:%M:00' , DATETIME(x.timestamp,'-' || (STRFTIME('%M', x.timestamp) % 5 ) || ' minutes' )) AS timestamp_rounded_to_5min FROM `data` x GROUP BY instance,timestamp_rounded_to_5min;



-- for hourly average the query is shorter, one can just format the date leaving the minutes and seconds and group over that:

SELECT AVG(value),instance, STRFTIME('%Y-%m-%d ' || '%H:00:00' , x.timestamp) AS timestamp_rounded_to_hour  FROM `data` x GROUP BY instance,timestamp_rounded_to_hour;


--- the testing table is 
CREATE TABLE `data` ( `id` BIGINT, `value` INT, `instance` TEXT, `TIMESTAMP` DATETIME, PRIMARY KEY(`id`) );

-- filling the dummy data

INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:21:11', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:22:53', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:23:29', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:24:30', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:25:03', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:26:20', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:27:19', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:28:37', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:29:06', 10, 'host');
INSERT INTO `data` (timestamp,value,instance) VALUES ('2013-10-07 08:30:44', 10, 'host');


-- queries just to compute the GROUP BY timestamp column (to be named timestamp again)

SELECT x.timestamp, '-' || STRFTIME('%M', x.timestamp) % 5 || ' minutes' AS d FROM `data` x;

SELECT x.timestamp, STRFTIME('%Y-%m-%d ' || '%H:%M:00' , DATETIME(x.timestamp,'-' || (STRFTIME('%M', x.timestamp) % 5 ) || ' minutes' )) AS t  FROM `data` x;

SELECT x.timestamp, STRFTIME('%Y-%m-%d ' || '%H:00:00' , x.timestamp) AS t  FROM `data` x;


