DELETE FROM TODOLIST.TODO_ITEM;
DELETE FROM TODOLIST.TODO_LIST;

DELETE FROM TODOLIST.USER_ROLES WHERE USER_ID=2;
DELETE FROM TODOLIST.USERS WHERE ID=2;

INSERT INTO todolist.USERS VALUES (2, 'username', 'password');
INSERT INTO todolist.USER_ROLES VALUES (2, 'ROLE_USER');

INSERT INTO TODOLIST.TODO_LIST VALUES (1, 'adminlist', 1);
INSERT INTO TODOLIST.TODO_LIST VALUES (2, 'userlist', 2);

INSERT INTO TODOLIST.TODO_ITEM VALUES (1, 'item1', 1, FALSE);
INSERT INTO TODOLIST.TODO_ITEM VALUES (2, 'item2', 1, FALSE);
INSERT INTO TODOLIST.TODO_ITEM VALUES (3, 'item3', 2, FALSE);
INSERT INTO TODOLIST.TODO_ITEM VALUES (4, 'item4', 2, FALSE);