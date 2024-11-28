package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldFetchAllTasks() {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        Task savedTask = taskRepository.save(task);
        Long id = savedTask.getId();
        //when
        List<Task> tasks = dbService.getAllTasks();
        //then
        assertEquals(1, tasks.size());
        assertEquals(savedTask.getId(), tasks.get(0).getId());
        assertEquals(savedTask.getTitle(), tasks.get(0).getTitle());
        assertEquals(savedTask.getContent(), tasks.get(0).getContent());
    }

    @Test
    void shouldFetchTaskById() throws TaskNotFoundException {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        Task savedTask = taskRepository.save(task);
        Long id = savedTask.getId();
        //when
        Task retrievedTask = dbService.getTaskById(id);
        //then
        assertEquals(savedTask.getId(), retrievedTask.getId());
        assertEquals(savedTask.getTitle(), retrievedTask.getTitle());
        assertEquals(savedTask.getContent(), retrievedTask.getContent());
    }

    @Test
    void shouldThrowExceptionIfNoTaskIdExists() {
        //given
        //when & then
        assertThrows(TaskNotFoundException.class, () -> dbService.getTaskById(1L));
    }

    @Test
    void shouldDeleteTask() {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        Task savedTask = taskRepository.save(task);
        Long id = savedTask.getId();
        //when
        dbService.deleteTaskById(id);
        List<Task> tasks = dbService.getAllTasks();
        //then
        assertEquals(0, tasks.size());
    }

    @Test
    void shouldCreateTask() {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        //when
        Task savedTask = taskRepository.save(task);
        //then
        assertEquals(task.getId(), savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getContent(), savedTask.getContent());
    }
}