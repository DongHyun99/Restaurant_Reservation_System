DROP DATABASE if EXISTS rrs;

CREATE DATABASE rrs ;

USE rrs ;

CREATE TABLE ttable (
       number    INT NOT NULL PRIMARY KEY,
       places    INT NOT NULL
) ;

CREATE TABLE User_inform (
       id VARCHAR(16) NOT NULL PRIMARY KEY,
       pw VARCHAR(16) NOT NULL,
       name VARCHAR(32) NOT NULL,
       phoneNumber CHAR(13) NOT NULL,
       penalty CHAR(2),
       admin CHAR(2)
) ;

CREATE TABLE WalkIn (
       covers    INT PRIMARY KEY,
       date        DATE,
       time        TIME,
       table_id    int
) ;

CREATE TABLE reservation (
      reservation_num   VARCHAR(10) PRIMARY KEY,
       covers       int,
       date           VARCHAR(20),
       time           VARCHAR(20),
       table_id       int,
       customer_id  VARCHAR(16) NOT NULL,
       arrivalTime  VARCHAR(20)
);

INSERT INTO User_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("mpolio2","1234","DongHyun", "01094318166", "F","F" );
INSERT INTO User_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("pony0528","1234","EujinPark", "01022820948", "F","T" );
INSERT INTO user_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("minina@naver.com","0000","minina","01012345678", "F","T" );
INSERT INTO user_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("jjh1159","123456","jjh","01088742165", "F","T" );
INSERT INTO user_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("rojin","1234","RojinPark","01011112222", "F","F" );
INSERT INTO user_inform (id, pw, NAME, phoneNumber, penalty, admin) VALUES ("roa","1234","RoaPark","01022223333", "T","F" );

INSERT INTO ttable (NUMBER, places) VALUES(1,1);
INSERT INTO ttable (NUMBER, places) VALUES(2,2);
INSERT INTO ttable (NUMBER, places) VALUES(3,3);

INSERT INTO reservation (reservation_num,covers,DATE,TIME,table_id,customer_id,arrivaltime) VALUES("0001","3","2021-05-19","19:00","2","mpolio2","18:30");
INSERT INTO reservation (reservation_num,covers,DATE,TIME,table_id,customer_id,arrivaltime) VALUES("0002","3","2021-05-19","12:00","2","pony0528","18:30");
INSERT INTO reservation (reservation_num,covers,DATE,TIME,table_id,customer_id,arrivaltime) VALUES("0003","3","2021-06-02","13:00","2","rojin","00:00");