package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {

    private final TaskRepository repository;
    private final TaskMapper taskMapper;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTaskById (final Long id) throws TaskNotFoundException {
        return repository.findById(id).orElseThrow(TaskNotFoundException::new);
    }

    public void deleteTaskById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Task> updateTask(TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        repository.save(task);
        return repository.findById(task.getId());
    }

    public Task saveTask(final Task task) {
        return repository.save(task);
    }
}
