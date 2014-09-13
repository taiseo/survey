CREATE TABLE TBCR_1000
(
  cst_no varchar2(16),
  cst_nm varchar2(100),
  hp varchar2(30),
  CONSTRAINT tbcr_1000_pk PRIMARY KEY (cst_no)
);


CREATE TABLE TBCR_1040
(
    cst_no varchar2(16),
    cat_cd varchar2(20),
    seq_no number(12),
    src_key varchar2(100),
    src_cd varchar2(2),
    ptcp_tit varchar2(500),
    ptcp_dttm varchar2(14),
    etc_01 varchar2(100),
    CONSTRAINT tbcr_1040_pk PRIMARY KEY (cst_no, cat_cd, seq_no),
    CONSTRAINT tbcr_1040_fk FOREIGN KEY (cst_no) REFERENCES TBCR_1000(cst_no)
);


insert all 
    into tbcr_1000 (cst_no, cst_nm, hp) values ('2011072800032841','홍길동','01088885555')
    into tbcr_1000 (cst_no, cst_nm, hp) values ('2011072800032051','김을동','01044445555')
    into tbcr_1000 (cst_no, cst_nm, hp) values ('2011072800033051','ahw','01044533153')
    into tbcr_1000 (cst_no, cst_nm, hp) values ('2011072800034051','kdg','01022206795')
select * from dual;
    
insert all
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800032841', '02', '1', '100001', '10', '목적외사업', '20140812000000', '제주지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800032841', '02', '2', '100001', '10', '목적외사업', '20140815000000', '광주지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800032051', '02', '1', '100001', '10', '목적외사업', '20140802000000', '경기도지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800032841', '03', '1', '100001', '20', '농지연금', '20140814000000', '경남지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800032051', '04', '1', '100001', '30', '경영이양직불', '20140808000000', '경남지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800033051', '02', '1', '100001', '10', '목적외사업', '20140812000000', '서울지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800033051', '02', '2', '100001', '10', '목적외사업', '20140815000000', '서울지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800034051', '02', '1', '100001', '10', '목적외사업', '20140802000000', '일산지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800033051', '03', '1', '100001', '20', '농지연금', '20140814000000', '일산지사')
    into tbcr_1040 (cst_no, cat_cd, seq_no, src_key, src_cd, ptcp_tit, ptcp_dttm, etc_01) values ('2011072800034051', '04', '1', '100001', '30', '경영이양직불', '20140808000000', '일산지사')
select * from dual;
    
