select title, content from icboard;

show tables;
select * from icboard;
select * from pjt;
select * from hotsource;
drop table aoptest;
drop table
--회원
CREATE TABLE members (
    id            VARCHAR(20)    NOT NULL, 
    name     VARCHAR(10)     NOT NULL,  
   passwd   VARCHAR(10)     NOT NULL, 
   ph_no     VARCHAR(20)    NULL,     
    email      VARCHAR(40)    NOT NULL  UNIQUE,
    birth        VARCHAR(10)    NOT NULL,
    joindate   TIMESTAMP         NOT NULL, 
    rating      VARCHAR(10)     NOT NULL,  
    PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
insert into members (id,name,passwd,ph_no,email,birth,joindate,rating)
values('master','master','1234','1234','master@master.com','today',now(),'M');

select * from members;

update members 
set rating='M'
where id='master'

DROP TABLE members;


--자유게시판
CREATE TABLE board (
  boardno   INT           NOT NULL PRIMARY KEY,
  title     VARCHAR(300)  NOT NULL,            
  content   VARCHAR(4000) NOT NULL,            
  id        VARCHAR(20)   NOT NULL,             
  date      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP NOT NULL,           
  readcnt   INT           DEFAULT 0 NOT NULL,            
  code      VARCHAR(5)    NOT NULL,             
  filename   VARCHAR(100)  	,
  filesize   INT				DEFAULT 0 NOT NULL,
  good      INT           DEFAULT 0 NOT NULL,         
  replycnt	   INT				DEFAULT 0
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

insert into board (boardno, title, content, id, readcnt, code, filename, good)
values((SELECT ifnull(MAX(boardno),0)+1 FROM board as TB),
       'TEST','IT IS TEST MaN','jisoo',14,'F','FILE',5);

insert into board (boardno, title, content, id, readcnt, code, filename, good)
values((SELECT ifnull(MAX(boardno),0)+1 FROM board as TB),
       'TEST1','IT 12 TEST MaN','TEST1',16,'F','FILE',5);
       
insert into board (boardno, title, content, id, readcnt, code, filename, good)
values((SELECT ifnull(MAX(boardno),0)+1 FROM board as TB),
       'TEST2','IT 23 TEST MaN','TEST2',6,'F','FILE',5);
       
insert into board (boardno, title, content, id, readcnt, code, filename, good)
values((SELECT ifnull(MAX(boardno),0)+1 FROM board as TB),
       'TEST3','IT 45 TEST MaN','TEST3',11,'F','FILE',5);
       
DROP TABLE board;      


--무슨쿼리?
SELECT boardno, title, id, date, readcnt, good, rank from(
select boardno,
       title,
       id,
       date,
       readcnt,
       good,       
       @vRank := @vRank +1 AS rank
FROM   board AS        )

--답글?
CREATE TABLE reply (
  rno       INT           NOT NULL PRIMARY KEY, 
  id        VARCHAR(20)   NOT NULL,             
  content   VARCHAR(1000) NOT NULL,             
  date      TIMESTAMP     NOT NULL,                          
  boardno   INT           NOT NULL,
  foreign key (boardno) references board(boardno) on delete cascade
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

SELECT * FROM reply

DROP TABLE reply;

CREATE TABLE helpboard(
    helpno   INT       	   NOT NULL,    
    title    VARCHAR(300)  NOT NULL,  
    content  VARCHAR(4000) NOT NULL,
    id    	 VARCHAR(10)   NOT NULL,
    date     TIMESTAMP     DEFAULT CURRENT_TIMESTAMP NOT NULL, 
    filename	VARCHAR(100),
	filesize	INT				DEFAULT 0 NOT NULL,
    readcnt  INT		   DEFAULT 0 NOT NULL, 
    passwd	 VARCHAR(20),                     
    locked	 VARCHAR(10)   NOT NULL,  
    PRIMARY KEY(helpno)
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

SELECT * FROM helpboard

DROP TABLE helpboard;


CREATE TABLE answer(
	ano 	INT 			NOT NULL, 
	title	VARCHAR(300)	NOT NULL, 
	content	VARCHAR(1000)	NOT NULL, 
	date	TIMESTAMP     	DEFAULT CURRENT_TIMESTAMP NOT NULL, 
	filename	VARCHAR(100),
	filesize	INT				DEFAULT 0 NOT NULL,
	helpno	INT				NOT NULL,
	PRIMARY KEY (ano),
	foreign key(helpno) REFERENCES helpboard(helpno) on delete cascade
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
	
DROP TABLE notice;

select * from answer;

--공지
CREATE TABLE notice(
	rnum		INT,
	noticeno	INT				NOT NULL, 
	code		VARCHAR(10) 	NOT NULL, 
	title		VARCHAR(300)	NOT NULL, 
	content		VARCHAR(4000)	NOT NULL, 
	answer		VARCHAR(4000)	, 
	date		TIMESTAMP     	DEFAULT CURRENT_TIMESTAMP NOT NULL,         
	readcnt		INT				DEFAULT 0 NOT NULL,
	filename	VARCHAR(100),
	filesize	INT				DEFAULT 0 NOT NULL,
	replycnt	   INT				DEFAULT 0,
	PRIMARY KEY(noticeno)
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

Drop Table notice;


INSERT INTO notice(noticeno, code, title, content, filename, filesize) 
VALUES ((SELECT IFNULL(MAX(noticeno),0)+1 FROM notice as NB),'N', 'test1', 'test1', 'test1', 0.0) 

SELECT * FROM notice;

select noticeno, title, readcnt
from notice
where code='F';



DELETE FROM notice WHERE noticeno = 2

DROP TABLE notice;

--??
CREATE TABLE ntreply (
  ntrno       INT           NOT NULL, 
  id        VARCHAR(20)   NOT NULL,             
  content   VARCHAR(1000) NOT NULL,             
  date      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP NOT NULL,                    
  noticeno   INT           NOT NULL,
  
  PRIMARY KEY (ntrno),
  foreign key (noticeno) references notice(noticeno) on delete cascade
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

SELECT * FROM ntreply
 
DROP TABLE ntreply;

select * from pjt;
--issue commit
CREATE TABLE icboard(
  rnum		   INT,
  icno         INT               NOT NULL,      
  prono        INT               NOT NULL,      
  title        VARCHAR(300)      NOT NULL,      
  content      VARCHAR(4000)    NOT NULL,      
  id           VARCHAR(20)       NOT NULL,       
  date         TIMESTAMP         DEFAULT CURRENT_TIMESTAMP NOT NULL, 
  readcnt      INT               DEFAULT 0 NOT NULL, 
  rcmcnt	   INT				 DEFAULT 0 NOT NULL,
  c_filename   VARCHAR(100)  	,
  c_filesize   INT				DEFAULT 0 NOT NULL,
  conditions   VARCHAR(10)      DEFAULT 'W',              
  code         VARCHAR(10)       NOT NULL,
  replycnt	   INT				DEFAULT 0,
  PRIMARY KEY(icno)            
) DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

DELETE FROM icboard WHERE icno = 1

SELECT * FROM icboard;

DROP TABLE icboard;


--
CREATE TABLE icreply (
  icrno       INT           NOT NULL, 
  id        VARCHAR(20)   NOT NULL,             
  content   VARCHAR(1000) NOT NULL,             
  date      TIMESTAMP     DEFAULT CURRENT_TIMESTAMP NOT NULL,                    
  icno   INT           NOT NULL,
  
  PRIMARY KEY (icrno),
  foreign key (icno) references icboard(icno) on delete cascade
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;


SELECT count(icrno) as cnt FROM icreply;
select * from icreply

DELETE FROM icreply WHERE icrno=4

DROP TABLE icreply;
	      
--test
SELECT a.*, COUNT(icreply.icrno) as replycnt
FROM ( 
       SELECT @rownum:=@rownum+1 as RNUM, b.* 
       FROM ( 
             SELECT icno,title,id,date,readcnt,rcmcnt 
             FROM icboard, (SELECT @rownum:=0)R
             WHERE code='I' ORDER BY icno ASC 
            ) b 
       ) a , icreply
 WHERE a.RNUM >=1 AND a.RNUM<=3 AND a.icno = icreply.icno
 GROUP BY a.icno
 ORDER BY RNUM DESC
 -------------------------------------------
SELECT a.* 
FROM ( 
       SELECT @rownum:=@rownum+1 as RNUM, b.* 
       FROM ( 
             SELECT icno,title,id,date,readcnt,rcmcnt 
             FROM icboard, (SELECT @rownum:=0)R
             WHERE code='I' ORDER BY icno ASC
            ) b 
       GROUP BY b.icno
 	   ORDER BY RNUM DESC
       ) a, icreply
 WHERE a.RNUM >=0 AND a.RNUM<=7
 
 
 
 
 select icboard.icno, count(icreply.icrno) as cnt
 from icboard, icreply
 where icboard.icno = icreply.icno
 group by icboard.icno
 
 select * from icreply
 
 select * from icboard
	      

show tables;
--------------------------------------------------------------------------------------
drop table myweb2;

create table Pjt(
Prono int(20) primary key not null,
Ptitle varchar(200) Not null,
Pname varchar(100) Not null,
Pexplain varchar(500) Not null,
Pcourse varchar(1000) Not null,
Pversion varchar(100) Not null,
Filesize int(100) Not null,
Id varchar(100) Not null,
Good int(100) DEFAULT 0 NOT NULL,
pjtcnt int(100) DEFAULT 0 NOT NULL,
Planguage varchar(100) NOT NULL,
Date date Not null
)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;
 

insert into Pjt
values(3,'wansik','test','PexplainTest', 'c/','v0.01',512,'wansik',null,'10',now());

select *
from pjt;

drop table pjt


 -------------main data-----
 
SELECT boardno, title, id, date, readcnt, good 
FROM board 
ORDER BY readcnt DESC, good DESC limit 5 

SELECT noticeno, title, readcnt
FROM notice
WHERE code='F';

SELECT Prono, Ptitle, Pname, Id, Date, Good
FROM Pjt
ORDER BY Good DESC limit 5;

create table exam (
examno int not null,
prono int not null,
downdate date NOT NULL,
downcnt int DEFAULT 0 not null,
PRIMARY KEY (examno)

)DEFAULT CHARSET=utf8 COLLATE utf8_general_ci;

--foreign key (prono) references Pjt(prono) on delete cascade

drop table exam

insert into exam (examno, prono, downdate,downcnt)
value((SELECT IFNULL(MAX(examno),0)+1 FROM exam as EX),2,'2018-09-14',30)

select * from exam

select * from exam 
where downdate >= '2018-01-01' AND downdate < '2018-09-01'
ORDER BY downcnt DESC


 