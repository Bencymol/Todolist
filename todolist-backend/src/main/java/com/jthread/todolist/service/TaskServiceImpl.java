package com.jthread.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jthread.todolist.entity.Todo;
import com.jthread.todolist.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	TaskRepository taskRepository;

	@Override
	public List<Todo> getAllTasks() {
		
		return taskRepository.findAll();
	}

	@Override
	public Todo addTask(Todo task) {
		
		return taskRepository.save(task);
	}

	@Override
	public Todo deleteTask(int id) {
		
		Todo todo = taskRepository.findById(id)
	             .orElse(null);

	    if (todo != null) {
	        taskRepository.deleteById(id);
	    }

	    return todo;
	}

	@Override
	public Todo updateTask(Todo task, int id) {
		Optional<Todo> optContainer = taskRepository.findById(id);
		if(optContainer.isEmpty()) {
			return null;
		}
		Todo updatedTask = new Todo();
		updatedTask.setId(task.getId());
		updatedTask.setStatus(task.getStatus());
		updatedTask.setTaskName(task.getTaskName());
		updatedTask.setUser(task.getUser());
		return taskRepository.save(updatedTask);
	}

	
	

}
