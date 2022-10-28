-- ������ΪMusicApp�����ݿ�
CREATE DATABASE MusicApp
GO
USE MusicApp
GO

-- ����User��
CREATE TABLE [User]
(
	u_id INTEGER  IDENTITY(1,1),
	u_username varchar(15),
	u_password varchar(20),
	u_sex CHAR(2)  CHECK(u_sex='��' OR u_sex='Ů'),
	u_age INTEGER,
	u_phone char(11) unique,
	u_region varchar(100),
	u_like varchar(100),
	u_type INTEGER CHECK(u_type=1 OR u_type=0),
	primary key(u_id),
)

-- ����Singer��
CREATE TABLE Singer
(
	s_id	INTEGER  IDENTITY(1,1),
	s_name	varchar(30),
	s_region varchar(30),
	s_intro	varchar(200),
	primary key(s_id),
)

-- ����Music��
CREATE TABLE Music
(
	m_id	INTEGER  IDENTITY(1,1),
	m_name	varchar(30),
	m_url	varchar(50),
	m_singer	INTEGER,
	m_type	INTEGER CHECK(m_type=1 OR m_type=0),
	m_userid INTEGER,
	primary key(m_id),
	FOREIGN KEY(m_userid) REFERENCES [User](u_id),
	FOREIGN KEY(m_singer) REFERENCES Singer(s_id),
)

-- ����Musiclist��
CREATE TABLE Musiclist
(
	ml_id INTEGER  IDENTITY(1,1),
	ml_name varchar(20),
	ml_userid INTEGER,
	ml_imgurl varchar(200),
	primary key(ml_id),
	FOREIGN KEY(ml_userid) REFERENCES [User](u_id),
)

-- ����Music_musiclist��
CREATE TABLE Music_musiclist
(
	mml_musicid	INTEGER ,
	mml_listid	INTEGER ,
	primary key(mml_musicid,mml_listid),
	FOREIGN KEY(mml_musicid) REFERENCES Music(m_id),
	FOREIGN KEY(mml_listid) REFERENCES Musiclist(ml_id),
)

-- ����Comment��
CREATE TABLE Comment
(
	c_id	INTEGER  IDENTITY(1,1),
	c_content	varchar(200),
	c_userid	INTEGER ,
	primary key(c_id),
	FOREIGN KEY(c_userid) REFERENCES [User](u_id),
)

-- ����Music_comment��
CREATE TABLE Music_commment
(
	mc_musicid INTEGER,
	mc_commentid INTEGER,
	primary key(mc_musicid,mc_commentid),
	FOREIGN KEY(mc_musicid) REFERENCES Music(m_id),
	FOREIGN KEY(mc_commentid) REFERENCES Comment(c_id),
)
