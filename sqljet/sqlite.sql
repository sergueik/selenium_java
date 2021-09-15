CREATE TABLE cache_table (
  id        mediumint  	AUTO_INCREMENT,
  ins_date  datetime     NOT NULL,
  fname     varchar(255) NOT NULL,
  ds        varchar(255) NOT NULL,
  expose    varchar(255) DEFAULT NULL,
  comment   varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ;
