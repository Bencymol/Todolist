package com.jthread.todolist.service;

import java.util.List;

import com.jthread.todolist.entity.Todo;

public interface TaskService {
	
	public List<Todo> getAllTasks();
	public Todo addTask(Todo task);
	public Todo deleteTask(int id);
	public Todo updateTask(Todo task,int id);

}
