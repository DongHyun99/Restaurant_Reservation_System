DROP DATABASE if EXISTS rrs;

CREATE DATABASE rrs ;

USE rrs ;

CREATE TABLE ttable (
       number	 INT NOT NULL PRIMARY KEY,
       places	 INT NOT NULL
) ;

CREATE TABLE User_inform (
       id VARCHAR(16) NOT NULL PRIMARY KEY,
       pw VARCHAR(16) NOT NULL,
       name VARCHAR(32) NOT NULL,
       phoneNumber CHAR(13) NOT NULL
) ;

CREATE TABLE WalkIn (
       covers	 INT PRIMARY KEY,
       date	     DATE,
       time	     TIME,
       table_id	 int
) ;

CREATE TABLE Reservation (
       covers	    int,
       date	        DATE,
       time	        TIME,
       table_id	    int,
       customer_id  INT PRIMARY KEY,
       arrivalTime  TIME
) ;

INSERT INTO User_inform (id, pw, NAME, phoneNumber) VALUES("mpolio2","1234","DongHyun", "01094318166");
INSERT INTO User_inform (id, pw, NAME, phoneNumber) VALUES("TestUser","1234","Test", "01012345678");

INSERT INTO ttable (NUMBER, places) VALUES(1,4);