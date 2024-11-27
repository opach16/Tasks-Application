package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService dbService;

    @Test
    void shouldFetchEmptyListOfTasks() throws Exception {
        List<TaskDto> tasksDto = List.of();
        List<Task> tasks = List.of();
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.<List<Task>>any())).thenReturn(tasksDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
        verify(dbService, times(1)).getAllTasks();
    }

    @Test
    void shouldFetchTasks() throws Exception {
        //given
        List<Task> tasks = List.of(new Task(1L, "testTitle", "testContent"));
        List<TaskDto> tasksDto = List.of(new TaskDto(1L, "testTitle", "testContent"));
        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(ArgumentMatchers.<List<Task>>any())).thenReturn(tasksDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("testTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("testContent")));
    }

    @Test
    void shouldFetchTaskById() throws Exception {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(dbService.getTaskById(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("testTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("testContent")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //given
        Task task = new Task(1L, "testTitle", "testContent");
        TaskDto taskDto = new TaskDto(1L, "testTitle", "testContent");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(dbService, times(1)).saveTask(task);
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //given
        Task updatedTask = new Task(1L, "updatedTitle", "updatedContent");
        TaskDto updatedTaskDto = new TaskDto(1L, "updatedTitle", "updatedContent");
        when(dbService.saveTask(updatedTask)).thenReturn(updatedTask);
        when(taskMapper.mapToTask(updatedTaskDto)).thenReturn(updatedTask);
        when(taskMapper.mapToTaskDto(updatedTask)).thenReturn(updatedTaskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(updatedTaskDto);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("updatedTitle")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("updatedContent")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //given
        doNothing().when(dbService).deleteTaskById(1L);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(dbService, times(1)).deleteTaskById(1L);
    }
}