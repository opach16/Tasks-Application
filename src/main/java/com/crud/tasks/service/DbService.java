package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;
    private final TaskMapper taskMapper;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById(final Long id) throws TaskNotFoundException {
        return repository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public void deleteTaskById(Long id) {
        repository.deleteById(id);
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }
}
