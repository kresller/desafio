DROP TABLE IF EXISTS USER;

CREATE TABLE USER (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  FIRST_NAME VARCHAR(250) NOT NULL,
  LAST_NAME VARCHAR(250) NULL,
  EMAIL VARCHAR(250)  NULL,
  BIRTHDAY DATE NULL,
  LOGIN VARCHAR(250) NULL,
  PASSWORD VARCHAR(250) NULL,
  PHONE VARCHAR(250) NULL,
  CREATED_AT	DATE NULL DEFAULT GETDATE(),
  LAST_LOGIN	DATE NULL
  
);

DROP TABLE IF EXISTS CAR;
 
CREATE TABLE CAR (
  ID INT AUTO_INCREMENT  PRIMARY KEY,
  USER_ID INT NOT NULL,
  YEAR VARCHAR(250) NOT NULL,
  LICENSE_PLATE VARCHAR(250) NOT NULL,
  MODEL VARCHAR(250) NULL,
  COLOR VARCHAR(250) NULL,
  QTD_USO INT NULL default 0
);
