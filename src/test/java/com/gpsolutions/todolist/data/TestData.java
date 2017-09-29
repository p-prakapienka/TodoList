package com.gpsolutions.todolist.data;

import com.gpsolutions.todolist.model.Role;
import com.gpsolutions.todolist.model.TodoItem;
import com.gpsolutions.todolist.model.TodoList;
import com.gpsolutions.todolist.model.User;
import java.util.Arrays;
import java.util.Collections;

public class TestData {

    public static User ADMIN = new User(1, "admin", "admin",
        Collections.singleton(Role.ROLE_ADMIN), Collections.emptyList());
    public static User ADMIN_ENC = new User(1, "admin", "$2a$10$tAp5BLhtTBFIDp4MQKjKWuV6zZ6ySRaSsYj5bYkkAEdUMOIss86z6",
        Collections.singleton(Role.ROLE_ADMIN), Collections.emptyList());
    public static User USER = new User(2, "username", "password",
        Collections.singleton(Role.ROLE_USER), Collections.emptyList());
    public static User USER_ENC = new User(2, "username", "$2a$10$g3EZu.7nPQEPF..feDjdv./cy4wnQWmAIZkBOxG01TZmsScKbSU0i",
        Collections.singleton(Role.ROLE_USER), Collections.emptyList());

    public static TodoList ADMIN_LIST = new TodoList(1, "adminlist", ADMIN);
    public static TodoList USER_LIST = new TodoList(2, "userlist", USER);

    static {
        ADMIN_LIST.setItems(Arrays.asList(
            new TodoItem(1,"item1", false),
            new TodoItem(2,"item2", false)
        ));
        USER_LIST.setItems(Arrays.asList(
            new TodoItem(3,"item3", false),
            new TodoItem(4, "item4", false)
        ));
    }

}
