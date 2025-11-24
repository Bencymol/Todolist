package com.jthread.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jthread.todolist.entity.Todo;

@Repository
public interface TaskRepository extends JpaRepository<Todo, Integer>{

}
