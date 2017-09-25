package com.gpsolutions.todolist.controller;

import com.gpsolutions.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TodoController {

    @Autowired
    protected TodoService todoService;

}
