DROP TABLE IF EXISTS todolist.todo_item;
DROP TABLE IF EXISTS todolist.todo_list;
DROP TABLE IF EXISTS todolist.user_roles;
DROP TABLE IF EXISTS todolist.users;
DROP SEQUENCE IF EXISTS todolist.global_seq;

CREATE SEQUENCE todolist.global_seq START WITH 100;

CREATE TABLE todolist.users (
  id        INTEGER PRIMARY KEY DEFAULT nextval('todolist.global_seq'),
  username  VARCHAR(255) NOT NULL,
  password  VARCHAR(255) NOT NULL
);

CREATE TABLE todolist.user_roles (
  user_id   INTEGER,
  role      VARCHAR(20),
  FOREIGN KEY (user_id) REFERENCES todolist.users (id)
);

CREATE TABLE todolist.todo_list (
  id        INTEGER PRIMARY KEY DEFAULT nextval('todolist.global_seq'),
  name      VARCHAR(255) NOT NULL,
  owner_id  INTEGER NOT NULL,
  FOREIGN KEY (owner_id) REFERENCES todolist.users(id)
);

CREATE TABLE todolist.todo_item (
  id        INTEGER PRIMARY KEY DEFAULT nextval('todolist.global_seq'),
  name      VARCHAR(255) NOT NULL,
  list_id   INTEGER NOT NULL,
  FOREIGN KEY (list_id) REFERENCES todolist.todo_list(id)
);

INSERT INTO todolist.users (username, password)
VALUES ('admin', '$2a$10$tAp5BLhtTBFIDp4MQKjKWuV6zZ6ySRaSsYj5bYkkAEdUMOIss86z6');

INSERT INTO todolist.user_roles (user_id, role)
VALUES (100, 'ROLE_ADMIN');