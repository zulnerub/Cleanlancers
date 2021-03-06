package com.simo.web.task.service;

import com.simo.web.task.model.TaskRegisterDTO;
import com.simo.web.task.model.TaskEntity;
import com.simo.web.task.model.TaskServiceDTO;

import java.util.List;


public interface TaskService {

    TaskEntity findById(Long id);

    List<TaskEntity> allTasks();

    List<TaskEntity> findAllOrderByCleanerAssigned();

    List<TaskServiceDTO> searchTasks(String taskName, String clientName, String cleanerName);


    void createTask(TaskRegisterDTO registerTaskDTO, String username);

    void delete(Long taskId);

    List<String> filterByTaskName(String taskName);

    List<String> filterByClientFirstName(String firstName);

    List<String> filterByClientLastName(String lastName);

    void updatedTask(TaskEntity task);
}
