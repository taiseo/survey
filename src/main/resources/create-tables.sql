CREATE TABLE sv_users
(
  id         NUMBER NOT NULL,
  username   VARCHAR2(30),
  password   VARCHAR2(40),
  name       VARCHAR2(20),
  part       VARCHAR2(30),
  tel        VARCHAR2(20),
  email      VARCHAR2(30),
  USER_LEVEL VARCHAR2(20) DEFAULT 'normal',
  CONSTRAINT sv_users_PK PRIMARY KEY (id), 
  CONSTRAINT sv_unique_users unique (username)
);

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

create table sv_surveys
(
  id            NUMBER NOT NULL,
  title         VARCHAR2(500),
  description   VARCHAR2(3000),
  start_date         VARCHAR2(10),
  end_date           VARCHAR2(10),
  target_category1   VARCHAR2(50),
  target_category2   VARCHAR2(50),
  target_branches    VARCHAR2(1000),
  writer             VARCHAR2(30),
  part          VARCHAR2(30),
  status        VARCHAR2(30) default '대기',
  datetime      DATE,
  constraint pk_sv_surveys primary key (id)
);


create sequence seq_sv_surveys start with 1 increment by 1 maxvalue 9999999999999999 cycle nocache;




create table sv_questions
(
  id               NUMBER NOT NULL,
  survey_id        NUMBER NOT NULL,
  content          VARCHAR2(500),
  content_detail   VARCHAR2(500),
  type             VARCHAR2(20),
  order_no         NUMBER DEFAULT 0,
  datetime         DATE,
  constraint pk_sv_questions primary key (id)
);

create sequence seq_sv_questions start with 1 increment by 1 maxvalue 9999999999999999 cycle nocache;


create table sv_response_items
(
  id            NUMBER NOT NULL,
  question_id   NUMBER NOT NULL,
  content       VARCHAR2(3000),
  order_no      NUMBER DEFAULT 0,
  datetime      DATE,
  constraint pk_sv_response_items primary key (id)
);

create sequence seq_sv_response_items start with 1 increment by 1 maxvalue 9999999999999999 cycle nocache;





create table sv_responses
(
  id              NUMBER NOT NULL,
  survey_id       NUMBER NOT NULL,
  question_id     NUMBER NOT NULL,
  respondent      VARCHAR2(300),
  response        VARCHAR2(1000),
  datetime        DATE,
  constraint pk_sv_responses primary key (id)
);

create sequence seq_sv_responses start with 1 increment by 1 maxvalue 9999999999999999 cycle nocache;



CREATE TABLE SV_CONFIG
(
  KEY_NAME     NUMBER NOT NULL,
  VALUE        VARCHAR2(1000),
  CONSTRAINT SV_CONFIG_PK PRIMARY KEY (KEY_NAME)
);