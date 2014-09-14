CREATE TABLE MMS_MSG
(
  MSGKEY     NUMBER NOT NULL,
  SUBJECT    VARCHAR2(120) NOT NULL,
  PHONE      VARCHAR2(15) NOT NULL,
  CALLBACK   VARCHAR2(15) NOT NULL,
  STATUS     VARCHAR2(2) DEFAULT '0' NOT NULL,
  REQDATE    DATE NOT NULL,
  MSG        VARCHAR2(4000),
  FILE_CNT   NUMBER DEFAULT 0,
  FILE_CNT_REAL   NUMBER DEFAULT 0,
  FILE_PATH1 VARCHAR2(128),
  FILE_PATH1_SIZ  NUMBER,
  FILE_PATH2 VARCHAR2(128),
  FILE_PATH2_SIZ  NUMBER,
  FILE_PATH3 VARCHAR2(128),
  FILE_PATH3_SIZ  NUMBER,
  FILE_PATH4 VARCHAR2(128),
  FILE_PATH4_SIZ  NUMBER,
  FILE_PATH5 VARCHAR2(128),
  FILE_PATH6_SIZ  NUMBER,
  EXPIRETIME VARCHAR(128) DEFAULT '43200' NOT NULL ,
  SENTDATE   DATE,
  RSLTDATE   DATE,
  REPORTDATE DATE,
  TERMINATEDDATE  DATE,
  RSLT       VARCHAR(10),
  REPCNT     NUMBER DEFAULT 0,
  TYPE       VARCHAR2(2) DEFAULT '0' NOT NULL,
  TELCOINFO  VARCHAR2(10),
  ID         VARCHAR2(20),
  POST       VARCHAR2(20),
  ETC1       VARCHAR2(64),
  ETC2       VARCHAR2(32),
  ETC3       VARCHAR2(32),
  ETC4       INT,
  CONSTRAINT MMS_MSG_PK PRIMARY KEY (MSGKEY)
);

create sequence MMS_MSG_SEQ start with 1 increment by 1 maxvalue 999999999999999 cycle nocache;
