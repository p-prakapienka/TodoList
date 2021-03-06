CREATE SCHEMA IF NOT EXISTS TODOLIST;

DROP TABLE IF EXISTS TODOLIST.TODO_ITEM;
DROP TABLE IF EXISTS TODOLIST.TODO_LIST;
DROP TABLE IF EXISTS TODOLIST.USER_ROLES;
DROP TABLE IF EXISTS TODOLIST.USERS;

CREATE TABLE TODOLIST.USERS (
  ID        INTEGER(10) NOT NULL AUTO_INCREMENT,
  USERNAME  VARCHAR(255) NOT NULL,
  PASSWORD  VARCHAR(255) NOT NULL,
  PRIMARY KEY (ID)
);

CREATE TABLE TODOLIST.USER_ROLES (
  USER_ID   INTEGER(10),
  ROLE      VARCHAR(20),
  FOREIGN KEY (USER_ID) REFERENCES TODOLIST.USERS (ID)
);

CREATE TABLE TODOLIST.TODO_LIST (
  ID        INTEGER(10) NOT NULL AUTO_INCREMENT,
  NAME      VARCHAR(255) NOT NULL,
  OWNER_ID  INTEGER(10) NOT NULL,
  FOREIGN KEY (OWNER_ID) REFERENCES TODOLIST.USERS(ID)
);

CREATE TABLE TODOLIST.TODO_ITEM (
  ID        INTEGER(10) NOT NULL AUTO_INCREMENT,
  NAME      VARCHAR(255) NOT NULL,
  LIST_ID   INTEGER(10) NOT NULL,
  FOREIGN KEY (LIST_ID) REFERENCES TODOLIST.TODO_LIST(ID)
);

INSERT INTO TODOLIST.USERS (USERNAME, PASSWORD)
VALUES ('admin', '$2a$10$tAp5BLhtTBFIDp4MQKjKWuV6zZ6ySRaSsYj5bYkkAEdUMOIss86z6');

INSERT INTO TODOLIST.USER_ROLES (USER_ID, ROLE) VALUES (1, 'ROLE_ADMIN');