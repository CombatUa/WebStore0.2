create table USERS
(
  ID NUMBER not null
    constraint USERS_ID_PK
    primary key,
  FIRST_NAME VARCHAR2(200),
  LAST_NAME VARCHAR2(200),
  SALARY NUMBER,
  DATE_OF_BIRTH DATE,
  PICTURE BLOB
)
/