package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void testMapToTaskDto() {
        //given
        Task task = new Task(1L, "test1", "test1");
        TaskDto expectedTaskDto = new TaskDto(1L, "test1", "test1");
        //when
        TaskDto retrievedTaskDto = taskMapper.mapToTaskDto(task);
        //then
        assertEquals(expectedTaskDto, retrievedTaskDto);
    }

    @Test
    void testMapToTask() {
        //given
        TaskDto taskDto = new TaskDto(1L, "test1", "test1");
        Task expectedTask = new Task(1L, "test1", "test1");
        //when
        Task retrievedTask = taskMapper.mapToTask(taskDto);
        //then
        assertEquals(expectedTask, retrievedTask);
    }

    @Test
    void testMapToTaskDtoList() {
        //given
        List<Task> taskList = List.of(
                new Task(1L, "test1", "test1"),
                new Task(2L, "test2", "test2"));
        List<TaskDto> expectedTaskDtoList = List.of(
                new TaskDto(1L, "test1", "test1"),
                new TaskDto(2L, "test2", "test2"));
        //when
        List<TaskDto> retrievedTaskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //then
        assertEquals(expectedTaskDtoList, retrievedTaskDtoList);
    }
}
