create database bdd_crypto_adviser;

CREATE TABLE  bdd_crypto_adviser.Adviser (
	 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
	 username VARCHAR( 60 ) NOT NULL ,
	 full_name VARCHAR( 60 ) NOT NULL ,
	 password VARCHAR( 32 ) NOT NULL ,
	 created_at DATETIME NOT NULL ,
	 PRIMARY KEY ( id ),
 	 UNIQUE ( username )
) ENGINE = INNODB;

CREATE TABLE  bdd_crypto_adviser.Trader (
	 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
	 username VARCHAR( 60 ) NOT NULL ,
	 full_name VARCHAR( 60 ) NOT NULL ,
	 password VARCHAR( 32 ) NOT NULL ,
	 created_at DATETIME NOT NULL ,
	 PRIMARY KEY ( id ),
 	 UNIQUE ( username )
) ENGINE = INNODB;

CREATE TABLE  bdd_crypto_adviser.Report (
	 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
	 adviser_id INT( 11 ) NOT NULL,
	 crypto_id INT( 11 ) NOT NULL,
	 title VARCHAR( 60 ) NOT NULL ,
	 content TEXT NOT NULL ,
	 created_at DATETIME NOT NULL ,
	 PRIMARY KEY ( id )
) ENGINE = INNODB;

CREATE TABLE  bdd_crypto_adviser.Crypto (
	 id INT( 11 ) NOT NULL AUTO_INCREMENT ,
	 Name VARCHAR( 60 ) NOT NULL ,
	 price DECIMAL( 11 ) NOT NULL,
	 last_update DATETIME NOT NULL ,
	 PRIMARY KEY ( id )
) ENGINE = INNODB;

CREATE TABLE  bdd_crypto_adviser.Comment (
	 comment_id INT( 11 ) NOT NULL AUTO_INCREMENT ,
	 username VARCHAR( 60 ) NOT NULL ,
	 report_id INT( 11 ) NOT NULL ,
	 content TEXT NOT NULL,
	 created_at DATETIME NOT NULL ,
	 PRIMARY KEY ( comment_id )
) ENGINE = INNODB;



