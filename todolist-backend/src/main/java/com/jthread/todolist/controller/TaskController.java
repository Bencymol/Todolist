package com.jthread.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jthread.todolist.entity.Todo;
import com.jthread.todolist.service.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:5173/",allowedHeaders = "*", allowCredentials = "true")
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	  @GetMapping("/")
	    public String home() {
	        return "Home Page";
	    }
	    
	    
	    
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @GetMapping("/admin/home")
	    public String getAdminHome() {
	        return "Admin Home Page";
	    }
	    
	    @GetMapping("/admin/tasklist")
	    public ResponseEntity<List<Todo>> getAllTasks(){
	    	System.out.println("Inside all tasks");
	    	 List<Todo> tasks = taskService.getAllTasks();
	    	 if (tasks.isEmpty()) {
	    	        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	    	    }
	    	return ResponseEntity.ok(tasks);
	    }
	    
	    @PostMapping("/admin/add-task")
	    public ResponseEntity<Todo> addTask(@RequestBody Todo task){
	    	Todo adtask = taskService.addTask(task);
	    	if (adtask.equals(null)) {
    	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    	    }
	    	return ResponseEntity.status(HttpStatus.CREATED).body(adtask);
	    }

	    @DeleteMapping("/admin/delete-task/{id}")
	    public ResponseEntity<Todo> deleteTask(@PathVariable(name="id") int taskId){
	    	Todo dlttask = taskService.deleteTask(taskId);
	    	return ResponseEntity.status(HttpStatus.OK).body(dlttask);
	    }
	    
	    @PutMapping("/admin/update-task/{id}")
	    public ResponseEntity<Todo> updateTask(@RequestBody Todo task, 
	    							@PathVariable(name="id") int taskId){
	    	Todo updatedTask = taskService.updateTask(task,taskId);
	    	if(updatedTask==null) {
	    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
	    }
	    

}
