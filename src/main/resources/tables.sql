CREATE TABLE sv_users
(
  id        NUMBER                              NOT NULL,
  username   VARCHAR2(30),
  password   VARCHAR2(40),
  name       VARCHAR2(20),
  part       VARCHAR2(30),
  tel        VARCHAR2(20),
  email      VARCHAR2(30),
  USER_LEVEL VARCHAR2(20) DEFAULT 'normal'
)
RESULT_CACHE (MODE DEFAULT)
STORAGE    (
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;


ALTER TABLE sv_users ADD (
  CONSTRAINT sv_users_PK
  PRIMARY KEY
  (id)
  ENABLE VALIDATE);

create sequence seq_sv_users start with 1 increment by 1 maxvalue 999999999999999 cycle nocache;

insert into sv_users 
  (id, username, password, name, part, tel, email, user_level) 
  values (
    seq_sv_users.nextval, 
    'admin', 
    '20f9aa76346956667051e58ff15d359bd8f4f2ad',
    '최고관리자',
    '최고관리자',
    '010-0000-0000',
    'email@email.com',
    'admin'
  );
