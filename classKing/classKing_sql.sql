--member--
CREATE TABLE member 
(
  member_ID VARCHAR2(20) NOT NULL 
, member_PWD VARCHAR2(100) NOT NULL 
, member_NAME VARCHAR2(20) NOT NULL 
, member_EMAIL VARCHAR2(50) NOT NULL 
, member_BIRTHDAY CHAR(6) NOT NULL 
, QUESTION VARCHAR2(100) NOT NULL 
, ANSWER VARCHAR2(50) NOT NULL 
, JOIN_DATE DATE DEFAULT sysdate NOT NULL 
, member_ORIGINAL_IMG VARCHAR2(30) 
, member_RENAME_IMG VARCHAR2(30) 
, member_NICKNAME VARCHAR2(20) NOT NULL 
, member_out CHAR(1) DEFAULT 'N' NOT NULL 
, CONSTRAINT member_PK PRIMARY KEY (member_ID) ENABLE
, CONSTRAINT member_out_ck CHECK (member_out IN('Y','N'))
);

COMMENT ON COLUMN member.member_id IS '멤버 아이디';
COMMENT ON COLUMN member.member_pwd IS '멤버 비밀번호';
COMMENT ON COLUMN member.member_name IS '멤버 이름';
COMMENT ON COLUMN member.member_email IS '멤버 아이디';
COMMENT ON COLUMN member.member_birthday IS '멤버 생일';
COMMENT ON COLUMN member.question IS '비번 질문 번호';
COMMENT ON COLUMN member.answer IS '비번 답변';
COMMENT ON COLUMN member.join_date IS '가입 날짜';
COMMENT ON COLUMN member.member_original_img IS '원본 이미지 파일';
COMMENT ON COLUMN member.member_rename_img IS '수정된 이미지 파일';
COMMENT ON COLUMN member.member_out IS '탈퇴';
COMMENT ON COLUMN member.member_nickname IS '닉네임';

--member--

--class category--
create table class_category(
  class_category_no char(2) primary key,
  class_category_name varchar2(20) not null
);

COMMENT ON COLUMN CLASS_CATEGORY.class_category_no is '클래스카테고리번호';
COMMENT ON COLUMN CLASS_CATEGORY.clasS_category_name is '클래스카테고리이름';

--class_category--

--class--
create table class(
  class_no number primary key,
  class_title varchar2(50) unique not null,
  class_subtitle varchar2(100) ,
  class_category_no char(2),--외래키
  class_open char(1) ,--체크y,n
  class_original_img varchar2(30),
  class_rename_img varchar2(30),
  create_date date default sysdate,
  class_close char(1),--체크y,n
  constraint class_open_ck CHECK (class_open IN('Y','N')),
  constraint class_close_ck CHECK (class_close IN('Y','N')),
  constraint class_category_no_fk FOREIGN KEY(class_category_no)
  references class_category(class_category_no)
);
--class--

--class_member--
create table class_member(
  class_member_no number primary key,
  class_no number,--외래키
  member_id varchar2(20),--외래키
  class_king char(1),--yn
  class_join_date date default sysdate,
  class_member_leave char(1) default 'N',--yn
  constraint cc_class_no_fk FOREIGN KEy(class_no)
  references class(class_no),
  constraint cc_member_fk FOREIGN KEY(member_id)
  references member(member_id),
  constraint cc_class_king_ck CHECK (class_king IN('Y','N')),
  constraint cc_class_member_leave_ck CHECK(class_member_leave IN('Y','N'))
);

COMMENT ON COLUMN CLASS_MEMBER.CLASS_NO IS '클래스 번호';
COMMENT ON COLUMN CLASS_MEMBER.CLASS_MEMBER_NO IS '클래스 회원 sequence';
COMMENT ON COLUMN CLASS_MEMBER.MEMBER_ID IS '회원 아이디';
COMMENT ON COLUMN CLASS_MEMBER.CLASS_KING IS '클래스 장(Y/N)';
COMMENT ON COLUMN CLASS_MEMBER.CLASS_JOIN_DATE IS '클래스 가입 날짜';
COMMENT ON COLUMN CLASS_MEMBER.CLASS_MEMBER_LEAVE IS '클래스 탈퇴여부';

--class_member--

--board_tag--
create table board_tag(
  board_tag_no char(2) primary key,
  board_tag_name varchar2(20) not null
);

comment on COLUMN board_tag.board_tag_no is '게시판 말머리';
comment on COLUMN board_tag.board_tag_name is '말머리 이름';

--board_tag--

--board--
create table board(
  board_no number primary key,
  class_member_no number,-- 외래키
  board_title varchar2(100) not null,
  board_note varchar2(3000) not null,
  board_date date default sysdate,
  board_like number default 0,
  board_tag_no char(2),--외래키
  board_view number default 0,
  board_original_file varchar2(30),
  board_rename_file varchar2(30),
  board_delete char(1) default 'N',
  CONSTRAINT board_delete_ck CHECK (board_delete IN('Y','N')),
  constraint board_cc_no_fk FOREIGN KEy(class_member_no) references class_member(class_member_no),
  constraint board_tag_no_fk FOREIGN KEy(board_tag_no) references board_tag(board_tag_no)
);

comment on COLUMN board.board_no is '게시글 번호';
comment on COLUMN board.class_member_no is '클래스_게시자_번호';
comment on COLUMN board.board_title is '게시글 제목';
comment on COLUMN board.board_note is '게시글 내용';
comment on COLUMN board.board_date is '게시글 작성일자';
comment on COLUMN board.board_like is '게시글 좋아요! 수';
comment on COLUMN board.board_tag_no is '말머리 종류(외래키: board_tag_no_fk)';
comment on COLUMN board.board_view is '조회수';
comment on COLUMN board.board_original_file is '첨부파일';
comment on COLUMN board.board_rename_file is '같은 첨부 파일명 일시 식별 컬럼';

--board--

--comments--
create table comments(
  comments_no number primary key,
  board_no number,--외래키
  member_id varchar(20),--외래키
  comments_date date default sysdate,
  comments_note varchar2(300) not null,
  comments_delete char(1) default 'N',
  CONSTRAINT com_delete_ck CHECK (comments_delete IN('Y','N')),
  constraint com_board_no_fk FOREIGN KEy(board_no) references board(board_no),
  constraint com_member_id_fk FOREIGN KEY (member_id) references member(member_id)
);

COMMENT ON COLUMN COMMENTs.COMMENTs_NO IS '댓글 sequence';
COMMENT ON COLUMN COMMENTs.BOARD_NO IS '게시글 번호';
COMMENT ON COLUMN COMMENTs.MEMBER_ID IS '회원ID';
COMMENT ON COLUMN COMMENTs.COMMENTs_DATE IS '댓글 등록 날짜';
COMMENT ON COLUMN COMMENTs.COMMENTs_NOTE IS '댓글 내용';

--comments--

--calendar--
create table calendar(
  cal_no number primary key,
  class_member_no number,--외래키
  cal_title varchar2(50) not null,
  cal_note varchar2(300) ,
  cal_sdate date not null,
  cal_edate date ,
  board_no number,--외래키
  constraint cal_cc_no_fk FOREIGN KEY(class_member_no) 
  references class_member(class_member_no),
  constraint cal_board_no_fk FOREIGN KEY(board_no) 
  references board(board_no)
);

COMMENT ON COLUMN CALENDAR.cal_no is '캘린더번호';
COMMENT ON COLUMN CALENDAR.cal_title is '캘린더제목';
COMMENT ON COLUMN CALENDAR.cal_note is '캘린더내용';
COMMENT ON COLUMN CALENDAR.cal_sdate is '일정시작날짜';
COMMENT ON COLUMN CALENDAR.cal_edate is '일정종료날짜';

--calendar--

--notice--
create table notice(
  notice_no number primary key,
  notice_title varchar2(100) not null,
  notice_note varchar2(3000) not null,
  notice_date date default sysdate,
  notice_view number default 0,
  notice_original_file varchar2(30),
  notice_rename_file varchar2(30)
);

COMMENT ON COLUMN NOTICE.NOTICE_NO IS '공지번호';
COMMENT ON COLUMN NOTICE.NOTICE_TITLE IS '공지 제목';
COMMENT ON COLUMN NOTICE.NOTICE_DATE IS '공지 작성일';
COMMENT ON COLUMN NOTICE.NOTICE_NOTE IS '공지내용';
COMMENT ON COLUMN NOTICE.NOTICE_VIEW IS '공지조회수';
COMMENT ON COLUMN NOTICE.NOTICE_ORIGINAL_FILE IS '공지첨부파일';
COMMENT ON COLUMN NOTICE.NOTICE_RENAME_FILE IS '공지첨부파일이름 바뀐것';

--notice--

--qna--
create table qna(
  qna_no number primary key,
  qna_writer varchar2(20) ,--외래키
  qna_title varchar2(100) not null,
  qna_note varchar2(3000) not null,
  qna_date date default sysdate,
  qna_view number default 0,
  qna_original_file varchar2(30),
  qna_rename_file varchar2(30),
  qna_answer varchar2(3000),
  answer_date date,
  constraint qna_writer FOREIGN KEY(qna_writer)
  references member(member_id)
);

COMMENT ON COLUMN QNA.QNA_NO IS '질문번호';
COMMENT ON COLUMN QNA.QNA_WRITER IS '질문작성자';
COMMENT ON COLUMN QNA.QNA_TITLE IS '질문제목';
COMMENT ON COLUMN QNA.QNA_DATE IS '질문작성일';
COMMENT ON COLUMN QNA.QNA_NOTE IS '질문내용';
COMMENT ON COLUMN QNA.QNA_VIEW IS '질문조회수';
COMMENT ON COLUMN QNA.QNA_ORIGINAL_FILE IS '질문첨부파일';
COMMENT ON COLUMN QNA.QNA_RENAME_FILE IS '질문첨부파일이름 바뀐것';
COMMENT ON COLUMN QNA.QNA_ANSWER IS '질문답변';

--qna--

--gallery--
create table gallery(
  gal_no number primary key,
  class_member_no number,
  gal_title varchar2(100) not null,
  gal_note varchar2(3000) not null,
  gal_date date default sysdate,
  gal_view number default 0,
  gal_like number default 0,
  gal_original_img varchar2(30),
  gal_rename_img varchar2(30),
  gal_original_img2 varchar2(30),
  gal_rename_img2 varchar2(30),
  gal_original_img3 varchar2(30),
  gal_rename_img3 varchar2(30),
  constraint gal_cm_no_fk FOREIGN KEY (class_member_no)
  references class_member(class_member_no )
);

COMMENT ON COLUMN gallery.gal_no IS '겔러리 넘버';
COMMENT ON COLUMN gallery.gal_title IS '겔러리 제목';
COMMENT ON COLUMN gallery.gal_note IS '겔러리 내용';
COMMENT ON COLUMN gallery.gal_like IS '겔러리 좋아요수';
COMMENT ON COLUMN gallery.gal_date IS '겔러리 날짜';
COMMENT ON COLUMN gallery.gal_view IS '겔러리 조회수';
COMMENT ON COLUMN gallery.gal_original_img IS '겔러리 원본 파일 이름';
COMMENT ON COLUMN gallery.gal_rename_img IS '겔러리 수정된 파일 이름';
COMMENT ON COLUMN gallery.gal_original_img2 IS '겔러리 원본 파일 이름2';
COMMENT ON COLUMN gallery.gal_rename_img2 IS '겔러리 수정된 파일 이름2';
COMMENT ON COLUMN gallery.gal_original_img3 IS '겔러리 원본 파일 이름3';
COMMENT ON COLUMN gallery.gal_rename_img3 IS '겔러리 수정된 파일 이름3';

--gallery--
--report_category--

create table report_category(
  report_category_no char(2) primary key,
  report_category_note varchar2(20)
);

comment on column report_category.report_category_note is '신고 항목';
comment on column report_category.report_category_no is '항목 번호';

--report_category--

--board_report--
create table board_report(
  report_no number primary key,
  member_id varchar2(20),--외래키
  board_no number,--외래키
  report_category_no char(2),--외래키
  report_note varchar2(300) not null,
  report_date date default sysdate,
  report_original_file varchar2(30),
  report_rename_file varchar2(30),
  report_fake char(1) default 0,
  constraint br_report_fake_ck CHECK (report_fake IN(0,1,2)),
  constraint br_member_id_fk FOREIGN KEY(member_id)
  references member(member_id),
  constraint br_board_no_fk FOREIGN KEY (board_no)
  references board(board_no),
  constraint br_report_cate_no_fk FOREIGN KEy(report_category_no)
  references report_category(report_category_no)
);

comment on column board_report.report_no is '신고 번호';
comment on column board_report.board_no is '게시글 번호';
comment on column board_report.report_category_no is '신고 항목';
comment on column board_report.report_note is '신고 내용';
comment on column board_report.report_date is '신고일';
comment on column board_report.member_id is '신고자';
comment on column board_report.report_original_file is '캡쳐원본 이름';
comment on column board_report.report_rename_file is '캡쳐수정 이름';
comment on column board_report.report_fake is '허위 여부';

--board_report--

--comments_report--
create table comments_report(
  report_no number primary key,
  member_id varchar2(20),--외래키
  comments_no number,--외래키
  report_category_no char(2),--외래키
  report_note varchar2(300) not null,
  report_date date default sysdate,
  report_original_filename varchar2(30),
  report_rename_filename varchar2(30),
  report_fake char(1) default 0,
  constraint cor_report_fake_ck CHECK (report_fake IN(0,1,2)),
  constraint cor_member_id_fk FOREIGN KEY(member_id)
  references member(member_id),
  constraint cor_comments_no_fk FOREIGN KEY (comments_no)
  references comments(comments_no),
  constraint cor_report_cate_no_fk FOREIGN KEY(report_category_no)
  references report_category(report_category_no)
);

comment on column comments_report.report_no is '신고 번호';
comment on column comments_report.comments_no is '댓글 번호';
comment on column comments_report.report_category_no is '신고 항목';
comment on column comments_report.report_note is '신고 내용';
comment on column comments_report.report_date is '신고일';
comment on column comments_report.member_id is '신고자';
comment on column comments_report.report_original_filename is '캡쳐원본 이름';
comment on column comments_report.report_rename_filename is '캡쳐수정 이름';
comment on column comments_report.report_fake is '허위 여부';

--comments_report--

--latest_access_date--
create table latest_access_date(
  class_member_no number,
  latest_date date,
  constraint lad_class_member_no_fk FOREIGN KEy(class_member_no) references class_member(class_member_no),
  PRIMARY KEY(class_member_no,latest_date )
);

--apply--
CREATE TABLE apply (
   apply_no number primary key,
   member_id varchar2(20),
   class_no number,
   apply_note varchar2(100),
   apply_date date default sysdate,
   apply_status char(1),
   constraint app_member_id_fk FOREIGN KEY (member_id) references member(member_id),
   constraint app_class_no_fk FOREIGN KEY (class_no) references class(class_no),
   constraint app_status_ck CHECK (apply_status IN(0,1,2))
);

--apply--

--gallery_comments--
CREATE TABLE gallery_comments (
  comments_no number primary key,
  gal_no number,--외래키
  member_id varchar(20),--외래키
  comments_date date default sysdate,
  comments_note varchar2(300) not null,
  constraint gcom_board_no_fk FOREIGN KEy(gal_no) references gallery(gal_no),
  constraint gcom_member_id_fk FOREIGN KEY (member_id) references member(member_id)
);
--gallery_comments--

--class_chat--
CREATE TABLE class_chat(
  chat_no number primary key,
  class_no number,
  member_id varchar2(20),
  chat_note varchar2(900),
  chat_date date default sysdate,
  constraint cchat_member_id_fk FOREIGN KEY (member_id) references member(member_id),
  constraint cchat_class_no_fk FOREIGN KEY (class_no) references class(class_no)
);
--class chat--

--chat_1on1--
CREATE TABLE chat_1on1(
  chat_no number primary key,
  member_id1 varchar2(20),
  member_id2 varchar2(20),
  constraint c11_member_id1_fk FOREIGN KEY (member_id1) references member(member_id),
  constraint c11_member_id2_fk FOREIGN KEY (member_id2) references member(member_id)
);

--chat_1on1--

--chat_1on1_note--
CREATE TABLE chat_1on1_note(
  chat_no number,
  member_id varchar2(20),
  chat_note varchar2(900),
  chat_date date default sysdate,
  constraint c11n_member_id_fk FOREIGN KEY (member_id) references member(member_id),
  constraint c11n_chat_no_fk FOREIGN KEY (chat_no) references chat_1on1(chat_no)
);
--chat_1on1_note--

--push--
create table push(
  push_no number primary key,
  board_no number not null,
  comments_writer varchar(20) not null,
  push_date date default sysdate,
  push_read char(1) DEFAULT 'N' NOT NULL, 
  constraint p_board_no FOREIGN KEy(board_no)
  references board(board_no),
  constraint p_comments_writer FOREIGN KEy(comments_writer)
  references member(member_id), 
  CONSTRAINT push_read_ck CHECK (push_read IN('Y','N'))
);

COMMENT ON COLUMN push.push_no is '알림번호';
COMMENT ON COLUMN push.board_no is '게시물번호';
COMMENT ON COLUMN push.comments_writer is '댓글작성자';
COMMENT ON COLUMN push.push_read is '확인여부';
COMMENT ON COLUMN push.push_date is '알림날짜';

--push--

insert into board_tag values('01','공지');
insert into board_tag values('02','잡담');
insert into board_tag values('03','질문');
insert into board_tag values('04','과제');
insert into board_tag values('05','기타');

insert into class_category values('01','운동');
insert into class_category values('02','스터디');
insert into class_category values('03','회사');
insert into class_category values('04','여행');
insert into class_category values('05','반려동물');
insert into class_category values('06','취미');

alter table gallery drop column gal_like;
alter table board drop column board_like;
alter table member drop column member_NICKNAME;
commit;
